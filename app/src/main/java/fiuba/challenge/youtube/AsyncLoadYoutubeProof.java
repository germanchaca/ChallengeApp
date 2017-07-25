/*
 * Copyright (c) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package fiuba.challenge.youtube;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Asynchronously load the tasks.
 * 
 * @author Yaniv Inbar
 */
class AsyncLoadYoutubeProof extends YoutubeAsyncTaskProof {

	static final String TAG = "YoutubeSampleActivity";
    static final int REQUEST_AUTHORIZATION = 1;
    protected final SubmitProofActivity context;
	private final String title;
    private final String description;

	AsyncLoadYoutubeProof(SubmitProofActivity submitActivity, String title, String description) {
    super(submitActivity);
      context = submitActivity;
        this.title = title;
        this.description = description;
  }

  @Override
  protected Video doInBackground() throws IOException {

          String VIDEO_FILE_FORMAT = "video/*";

	Uri videoUri = activity.videoUri;
	try {
		// We get the user selected local video file to upload.

	      File videoFile = getFileFromUri(videoUri);
	      Log.d(TAG,"Elegiste " + videoFile  + " para subir.");

	      // Add extra information to the video before uploading.
	      Video videoObjectDefiningMetadata = new Video();

	      /*
	       * Set the video to unlisted, so only someone with the link can see it.  You will probably
	       * want to remove this in your code.  The default is public, which is what most people want.
	       */
	      VideoStatus status = new VideoStatus();
	      status.setPrivacyStatus("public");
	      videoObjectDefiningMetadata.setStatus(status);

	      // We set a majority of the metadata with the VideoSnippet object.
	      VideoSnippet snippet = new VideoSnippet();

	      /*
	       * The Calendar instance is used to create a unique name and description for test purposes,
	       * so you can see multiple files being uploaded.  You will want to remove this from your
	       * project and use your own standard names.
	       */
	      Calendar cal = Calendar.getInstance();
	      snippet.setTitle(title);
	      snippet.setDescription(description);

	      // Set your keywords.
	      List<String> tags = new ArrayList<String>();
	      tags.add("test");
	      tags.add("example");
	      tags.add("java");
	      tags.add("android");
	      tags.add("YouTube Data API V3");
	      tags.add("erase me");
	      snippet.setTags(tags);

	      // Set completed snippet to the video object.
	      videoObjectDefiningMetadata.setSnippet(snippet);

	      InputStreamContent mediaContent =
	    		  new InputStreamContent(VIDEO_FILE_FORMAT,
	                                 new BufferedInputStream(new FileInputStream(videoFile)));
	      mediaContent.setLength(videoFile.length());
	      Log.d(TAG,"videoFile.length()="+videoFile.length());
	      /*
	       * The upload command includes:
	       *   1. Information we want returned after file is successfully uploaded.
	       *   2. Metadata we want associated with the uploaded video.
	       *   3. Video file itself.
	       */
	      YouTube.Videos.Insert videoInsert =
	          youtube.videos().insert("snippet,statistics,status",
	                                  videoObjectDefiningMetadata,
	                                  mediaContent);

	      // Set the upload type and add event listener.
	      MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();


	      /*
	       * Sets whether direct media upload is enabled or disabled.
	       * True = whole media content is uploaded in a single request.
	       * False (default) = resumable media upload protocol to upload in data chunks.
	       */
	      uploader.setDirectUploadEnabled(false);
	      uploader.setChunkSize(1024*1024);
	      MediaHttpUploaderProgressListener progressListener =
	          new MediaHttpUploaderProgressListener() {
	            public void progressChanged(MediaHttpUploader uploader)
	                throws IOException {
	              switch (uploader.getUploadState()) {
	                case INITIATION_STARTED:
	                  Log.d(TAG,"Initiation Started");
	                  break;
	                case INITIATION_COMPLETE:
	                  Log.d(TAG,"Initiation Completed");
	                  break;
	                case MEDIA_IN_PROGRESS:
	                  Log.d(TAG,"Upload in progress");
	                  Log.d(TAG,"Upload percentage: " + uploader.getProgress());
	                  publishProgress((int) (uploader.getProgress()*100));
	                  break;
	                case MEDIA_COMPLETE:
	                  Log.d(TAG,"Upload Completed!");
	                  break;
	                case NOT_STARTED:
	                  Log.d(TAG,"Upload Not Started!");
	                  break;
	              }
	            }
	          };
	      uploader.setProgressListener(progressListener);

	      // Execute upload.
        Video returnedVideo = null;
		try {
            returnedVideo = videoInsert.execute();
            String videoId = returnedVideo.getId();

            Log.d(TAG, String.format("videoId = [%s]", videoId));
			context.endSubmit(videoId);
		} catch (UserRecoverableAuthIOException e) {

            ((Activity) context).startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
		}

	      return returnedVideo;
	      // Print out returned results.



	} catch (GoogleJsonResponseException e) {
	      Log.e(TAG,"GoogleJsonResponseException code: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
	      e.printStackTrace();

	} catch (IOException e) {
		  Log.e(TAG,"IOException: " + e.getMessage());
	      e.printStackTrace();
    } catch (Throwable t) {
	      Log.e(TAG,"Throwable: " + t.getMessage());
	      t.printStackTrace();
    }

	return null;

  }

  
  static void run(SubmitProofActivity submitActivity, String title, String description) {
    new AsyncLoadYoutubeProof(submitActivity,title,description).execute();
  }
  
  private File getFileFromUri(Uri uri) throws IOException {
    
	  Log.d(TAG,"uri = " + uri);
	  
	Cursor cursor = activity.managedQuery(uri, null, null, null, null);
    if (cursor.getCount() == 0) {
      throw new IOException(String.format("cannot find data from %s", uri.toString()));
    } else {
      cursor.moveToFirst();
    }

    String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));

    File file = new File(filePath);
    cursor.close();
    return file;
  }



}
