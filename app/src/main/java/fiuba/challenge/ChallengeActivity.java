package fiuba.challenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import fiuba.challenge.adapter.GridListAdapter;
import fiuba.challenge.adapter.OpenChallengesListAdapter;
import fiuba.challenge.model.Challenge;
import fiuba.challenge.model.Proof;
import fiuba.challenge.youtube.Utils;

public class ChallengeActivity  extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    private static final String TAG = "ChallengeActivity";
    protected RecyclerView mRecyclerView;
    protected GridListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected TextView challengeTitleTextView;

    protected static int GRID_COLS = 2;
    protected YouTubePlayerView rulesVideoView;

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    public YouTubePlayer youtubePlayer;
    private String videoId;
    private Challenge challenge;
    private Button seeRulesButton;

    @Override
    protected void onRestart() {
        rulesVideoView.initialize(Utils.API_KEY, this);
        Log.d(TAG, "onRestart called");
        super.onRestart();
    }

    public void setVideoId(String videoId) {
        Log.d(TAG, "setVideoId called");
        if (videoId != null) {
            Log.d(TAG, "valid videoId");
            this.videoId = videoId;
            if(this.youtubePlayer != null){
                youtubePlayer.loadVideo(videoId);
            }else {
                Log.e(TAG, "youtubePlayer is null");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        challenge = (Challenge) getIntent().getSerializableExtra(OpenChallengesListAdapter.CHALLENGE_INTENT);
        videoId = challenge.getRulesVideoId();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_challenge);
        challengeTitleTextView = (TextView) findViewById(R.id.challengeTitleTextView);
        rulesVideoView = (YouTubePlayerView) findViewById(R.id.rulesVideoView);
        mRecyclerView = (RecyclerView) findViewById(R.id.openChallengesRecyclerView);
        seeRulesButton = (Button) findViewById(R.id.seeRulesButton);

        seeRulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVideoId(challenge.getRulesVideoId());
            }
        });

        initVideoView();

        mLayoutManager = new GridLayoutManager(this,GRID_COLS);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //List proofs = getListProofsExample();

        mAdapter = new GridListAdapter(challenge.getProofs(),videoId);

        mRecyclerView.setAdapter(mAdapter);
    }

    @NonNull
    private List getListProofsExample() {
        List proofs = new ArrayList<Proof>();
        Proof p1 = new Proof("Y_UmWdcTrrc","Charlie", null);
        Proof p2 = new Proof("1KhZKNZO8mQ","Sam", null);
        Proof p3 = new Proof("UiLSiqyDf4Y","Joe", null);
        Proof p4 = new Proof("UiLSiqyDf4Y","Santi", null);

        proofs.add(p1);
        proofs.add(p2);
        proofs.add(p3);
        proofs.add(p4);
        proofs.add(p1);
        proofs.add(p2);
        proofs.add(p3);
        proofs.add(p4);
        proofs.add(p1);
        proofs.add(p2);
        proofs.add(p3);
        proofs.add(p4);
        return proofs;
    }

    private void initVideoView() {
        int screenWidth = dpToPx(getResources().getConfiguration().screenWidthDp);

        int youtubeThumbnailHeight = (screenWidth * 9 / 16);

        rulesVideoView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, youtubeThumbnailHeight));

        rulesVideoView.initialize(Utils.API_KEY, this);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);

    }
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.youtubePlayer = player;
        mAdapter.setPlayer(player);
        if (!wasRestored) {
        // loadVideo() will auto play video
        // Use cueVideo() method, if you don't want to play it automatically
                this.youtubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                this.youtubePlayer.cueVideo(videoId);
            }
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
           // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Utils.API_KEY, this);
        }
    }
    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.rulesVideoView);
     }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            Toast.makeText(this, errorReason.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy called");

        if (youtubePlayer != null) {
            youtubePlayer.release();
        }
        if(mAdapter != null){
            mAdapter.releaseLoaders();
        }

        super.onDestroy();
    }

}
