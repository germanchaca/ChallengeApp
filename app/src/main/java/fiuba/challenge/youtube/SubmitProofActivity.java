package fiuba.challenge.youtube;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import fiuba.challenge.CreateChallengeActivity;
import fiuba.challenge.LoginActivity;
import fiuba.challenge.MainActivity;
import fiuba.challenge.R;
import fiuba.challenge.model.ChallengeWrapper;
import fiuba.challenge.model.Proof;
import fiuba.challenge.model.ProofWrapper;

public class SubmitProofActivity extends Activity {

	  private static final Level LOGGING_LEVEL = Level.OFF;
	  private static final String PREF_ACCOUNT_NAME = "accountName";
	  static final String TAG = "YoutubeSampleActivity";
	  static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
	  static final int REQUEST_AUTHORIZATION = 1;
	  static final int REQUEST_ACCOUNT_PICKER = 2;

	private String FIREBASE_URL = "https://challenge-6f44d.firebaseio.com/";
	private String FIREBASE_CHILD = "challenges";

	Firebase firebaseChallenges;

	/** Global instance of Youtube object to make all API requests. */
	  YouTube youtube;
	  int numAsyncTasks;
	  
	  /* Global instance of the format used for the video being uploaded (MIME type). */
	  private static String VIDEO_FILE_FORMAT = "video/*";
	  
	  Uri videoUri = null;
	  TextView textViewStatus ;
	  ProgressDialog dialog = null;
	  
	  private Date dateTaken = null;
	  private Long uploadFileSize = null;
	  private GoogleAccountCredential credential;

    final HttpTransport httpTransport =  AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = new GsonFactory();
	private String challengeTitle;
	private String challengeDescription;
	private String challengeId;

	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		//TODO: no se esta viendo el toolbar
        //TODO: no cambiar diseno xml
	    // enable logging
	    Logger.getLogger("com.google.api.client").setLevel(LOGGING_LEVEL);

	    setContentView(R.layout.activity_submit);

		Firebase.setAndroidContext(this);

		challengeId = (String) getIntent().getStringExtra(YoutubeProofUploadActivity.CHALLENGE_ID);
		challengeTitle = (String) getIntent().getStringExtra(CreateChallengeActivity.TITLE);
		challengeDescription = (String) getIntent().getStringExtra(CreateChallengeActivity.DESCRIPTION);

		credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(YouTubeScopes.YOUTUBE_UPLOAD));
		SharedPreferences settings = getSharedPreferences(LoginActivity.APP,Context.MODE_PRIVATE);
		credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
        credential.setBackOff(new ExponentialBackOff());

		youtube = new YouTube.Builder(httpTransport, jsonFactory, credential).setApplicationName("Google-TasksAndroidSample/1.0").build();

	    Intent intent = this.getIntent();
	    this.videoUri = intent.getData();
	    Log.d(TAG,intent.getData().toString());
	    
	    Cursor cursor = this.getContentResolver().query(this.videoUri, null, null, null, null);

	    if (cursor.getCount() == 0) {
	      Log.d("cursor==", "not a valid video uri");
	      Toast.makeText(SubmitProofActivity.this, "not a valid video uri", Toast.LENGTH_LONG).show();
	    } else {
	      
	      if (cursor.moveToFirst()) {
	        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID));
	        this.dateTaken = new Date(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_TAKEN)));
	        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa");
	        Configuration userConfig = new Configuration();
	        Settings.System.getConfiguration(getContentResolver(), userConfig);
	        Calendar cal = Calendar.getInstance(userConfig.locale);
	        TimeZone tz = cal.getTimeZone();
	        dateFormat.setTimeZone(tz);
	        
	        this.uploadFileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE));

	        TextView dateTakenView = (TextView) findViewById(R.id.dateCaptured);
	        dateTakenView.setText(" - Fecha de captura: " + dateFormat.format(dateTaken) + "\n - Tamano de archivo: " + uploadFileSize/1024 + " KB ");

	        ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
	        ContentResolver crThumb = getContentResolver();
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 2;
	        Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id,
	            MediaStore.Video.Thumbnails.MINI_KIND, options);
	        thumbnail.setImageBitmap(curThumb);

	        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
	      }
	    }
	    // startUpload(SubmitActivity.this.videoUri);
	    findViewById(R.id.uploadButton).setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  if (checkGooglePlayServicesAvailable()) {
		    		      haveGooglePlayServices();
		    	  }
		      }
		    });
	  }
	  void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
		    runOnUiThread(new Runnable() {
		      public void run() {
		        Dialog dialog =
		            GooglePlayServicesUtil.getErrorDialog(connectionStatusCode, SubmitProofActivity.this,
		                REQUEST_GOOGLE_PLAY_SERVICES);
		        dialog.show();
		      }
		    });
		  }

	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    switch (requestCode) {
	      case REQUEST_GOOGLE_PLAY_SERVICES:
	        if (resultCode == Activity.RESULT_OK) {
	          haveGooglePlayServices();
	        } else {
	          checkGooglePlayServicesAvailable();
	        }
	        break;
	      case REQUEST_AUTHORIZATION:
	        if (resultCode == Activity.RESULT_OK) {
	        	AsyncLoadYoutubeProof.run(this,challengeTitle,challengeDescription);
	        } else {
	          chooseAccount();
	        }
	        break;
	      case REQUEST_ACCOUNT_PICKER:
	        if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
	          String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
	          if (accountName != null) {
	            credential.setSelectedAccountName(accountName);
	            SharedPreferences settings = getSharedPreferences(LoginActivity.APP,Context.MODE_PRIVATE);
	            SharedPreferences.Editor editor = settings.edit();
	            editor.putString(PREF_ACCOUNT_NAME, accountName);
	            editor.commit();
	            AsyncLoadYoutubeProof.run(this,challengeTitle,challengeDescription);
	          }
	        }
	        break;
	    }
	  }

	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.youtube_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	  }

	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case R.id.menu_refresh:
	        //AsyncLoadYoutube.run(this);
	    	
	        break;
	      case R.id.menu_accounts:
	        chooseAccount();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	  }

	  /** Check that Google Play services APK is installed and up to date. */
	  private boolean checkGooglePlayServicesAvailable() {
	    final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
	      showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
	      return false;
	    }
	    return true;
	  }

	  private void haveGooglePlayServices() {
		Log.d(TAG,"accountName="+credential.getSelectedAccountName());
	    // check if there is already an account selected
	    if (credential.getSelectedAccountName() == null) {
	      // ask user to choose account
	      chooseAccount();
	    } else {
	      // upload youtube.

	    	AsyncLoadYoutubeProof.run(this,challengeTitle,challengeDescription);
	    }
	  }

	public void endSubmit(String videoId){

        final ProofWrapper proof = new ProofWrapper();

		proof.setUsername( credential.getSelectedAccountName());
		proof.setVideoId(videoId);

		firebaseChallenges = new Firebase(FIREBASE_URL).child(FIREBASE_CHILD).child(challengeId).child("proofs");

        Firebase newPostRef = firebaseChallenges.push();

        newPostRef.setValue(proof, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved " + firebaseError.getMessage());
                } else {
                    Log.d(TAG,"Data saved successfully.");

                   AlertDialog.Builder builder =
                            new AlertDialog.Builder(SubmitProofActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    builder.setTitle("Todo listo");
                    builder.setMessage("Su video fue cargado con éxito");
                    builder.setIcon(R.drawable.ic_done_black_24dp);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            Log.d(TAG,"Exito");
                            // mandar al mainActivity
							Intent intent = new Intent(SubmitProofActivity.this, MainActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
                        }

                    });
                    builder.show();

                }
            }
        });

	}

	  private void chooseAccount() {
	    startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
	  }

	  @Override	
	  protected Dialog onCreateDialog(int id) {
		  
		  if ( id==1 ){
			  dialog = new ProgressDialog(this);
			  dialog.setTitle("upload video on youtube");
			  dialog.setMessage("upload video on youtube");
			  dialog.setMax(100);
			  dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			  
		  }
		  return dialog;
	  }


}