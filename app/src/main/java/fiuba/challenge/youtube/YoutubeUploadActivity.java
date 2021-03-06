package fiuba.challenge.youtube;

import java.util.List;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import fiuba.challenge.R;
import fiuba.challenge.youtube.SubmitActivity;

public class YoutubeUploadActivity extends Activity {

	  private static final String LOG_TAG = YoutubeUploadActivity.class.getSimpleName();
	  private static final int CAPTURE_RETURN = 1;
	  private static final int GALLERY_RETURN = 2;
	  private static final int SUBMIT_RETURN = 3;
      private static final int REQUEST_CAMERA = 11;
      private static final int REQUEST_GALLERY = 12;
    private static final int REQUEST_GET_ACCOUNTS = 13 ;
    private Intent data;

    public void capture(){
        Intent i = new Intent();
        i.setAction("android.media.action.VIDEO_CAPTURE");
        startActivityForResult(i, CAPTURE_RETURN);
    }

    @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_youtube_upload);

	    findViewById(R.id.captureButton).setOnClickListener(new OnClickListener() {
	      @TargetApi(Build.VERSION_CODES.M)
          @Override
	      public void onClick(View v) {
			  if (checkSelfPermission(Manifest.permission.CAMERA)
					  != PackageManager.PERMISSION_GRANTED) {
				  requestPermissions(new String[]{Manifest.permission.CAMERA},
						  REQUEST_CAMERA);
			  }else{
                  capture();
              }
	      }
	    });

	    findViewById(R.id.galleryButton).setOnClickListener(new OnClickListener() {
	      @TargetApi(Build.VERSION_CODES.M)
          @Override
	      public void onClick(View v) {

              if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                      != PackageManager.PERMISSION_GRANTED) {
                  requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                          REQUEST_GALLERY);
              }else{
                  startGallery();
              }

	      }
	    });
	  }

    public void startGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("video/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() <= 0) {
            Log.d(LOG_TAG, "no video picker intent on this hardware");
            return;
        }

        startActivityForResult(intent, GALLERY_RETURN);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                capture();
            }
            else {
            }
        }
        if (requestCode == REQUEST_GALLERY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
            }
            else {
            }
        }
        if (requestCode == REQUEST_GET_ACCOUNTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSubmitActivity(this.data);
            }
            else {
            }
        }
    }
	  @TargetApi(Build.VERSION_CODES.M)
      @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    switch (requestCode) {
	    case CAPTURE_RETURN:
	    case GALLERY_RETURN:
	      if (resultCode == RESULT_OK) {
              if (checkSelfPermission(Manifest.permission.GET_ACCOUNTS)
                      != PackageManager.PERMISSION_GRANTED) {
                  requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},
                          REQUEST_GET_ACCOUNTS);
              }else{
                  if (checkSelfPermission(Manifest.permission.GET_ACCOUNTS)
                          != PackageManager.PERMISSION_GRANTED) {
                      requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},
                              REQUEST_GET_ACCOUNTS);
                  }else{
                      startSubmitActivity(data);
                  }
              }

            this.data = data;

	      }
	      break;
	    case SUBMIT_RETURN:
	      if (resultCode == RESULT_OK) {
	        Toast.makeText(YoutubeUploadActivity.this, "thank you!", Toast.LENGTH_LONG).show();
              String videoId = data.getExtras().getString("VIDEOID");
              Log.d("YoutubeUploadActivity",videoId );
          } else {
	      }
	      break;
	    }
	  }

    public void startSubmitActivity(Intent data){
        Intent intent = new Intent(this, SubmitActivity.class);
        intent.setData(data.getData());
        startActivityForResult(intent, SUBMIT_RETURN);
    }

	  @Override
	  public void onStart() {
	    super.onStart();
	  }

	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	  }


}
