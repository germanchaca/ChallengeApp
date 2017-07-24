package fiuba.challenge;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fiuba.challenge.adapter.OpenChallengesListAdapter;
import fiuba.challenge.model.Challenge;
import fiuba.challenge.model.Proof;

public class OpenChallengesFragment extends Fragment {

    public static final String TAG = "OpenChallengesFragment";
    protected RecyclerView mRecyclerView;
    protected OpenChallengesListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    private String FIREBASE_URL = "https://challenge-6f44d.firebaseio.com/";
    private String FIREBASE_CHILD = "challenges";

    Firebase firebase;

    public OpenChallengesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_open_challenges, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.openChallengesRecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Firebase.setAndroidContext(getContext());

        final List challenges = new ArrayList<Challenge>();

        firebase = new Firebase(FIREBASE_URL).child(FIREBASE_CHILD);
        firebase.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot snapshot) {
                challenges.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    String challengeId = data.getKey();
                    Log.d(TAG, "challengeId: " + challengeId);

                    Challenge challenge = new Challenge();

                    for (DataSnapshot challengeSnapshot : data.getChildren()) {
                        switch (challengeSnapshot.getKey().toString()){
                            case "creationDate":
                                Long creationDate =  Long.getLong(challengeSnapshot.getValue().toString());
                                challenge.setCreationDate(creationDate);
                                break;

                            case "description":
                                String description =  challengeSnapshot.getValue().toString();
                                challenge.setDescription(description);
                                break;

                            case "title":
                                String title =  challengeSnapshot.getValue().toString();
                                challenge.setTitle(title);
                                break;

                            case "username":
                                String username =  challengeSnapshot.getValue().toString();
                                challenge.setUsername(username);
                                break;

                            case "videoId":
                                String videoId =  challengeSnapshot.getValue().toString();
                                challenge.setRulesVideoId(videoId);
                                break;

                            case "proofs":

                                for (DataSnapshot proofData : challengeSnapshot.getChildren()) {
                                    String proofId = proofData.getKey();
                                    Log.d(TAG, "proofId: " + proofId);
                                    Proof proof = new Proof();
                                    proof.setChallenge(challenge);
                                    for (DataSnapshot proofSnapshot : proofData.getChildren()) {
                                        switch (proofSnapshot.getKey().toString()) {
                                            case "creationDate":
                                                Long proofCreationDate =  Long.getLong(proofSnapshot.getValue().toString());
                                                proof.setCreationDate(proofCreationDate);
                                                break;
                                            case "username":
                                                String proofUsername =  proofSnapshot.getValue().toString();
                                                proof.setUsername(proofUsername);
                                                break;
                                            case "videoId":
                                                String proofVideoId =  proofSnapshot.getValue().toString();
                                                proof.setVideoId(proofVideoId);
                                                break;
                                        }
                                        Log.d(TAG, "Key: " + proofSnapshot.getKey().toString() + ", Value: " + proofSnapshot.getValue().toString());

                                    }
                                    challenge.addProof(proof);
                                }
                                break;

                        }
                        Log.d(TAG, "Key: " + challengeSnapshot.getKey().toString() + ", Value: " + challengeSnapshot.getValue().toString());
                    }
                    challenges.add(challenge);
                }
                mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(FirebaseError error) {
            }

        });


        //List challenges = getChallengeListExample();

        mAdapter = new OpenChallengesListAdapter(this.getContext(),challenges);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @NonNull
    private List getChallengeListExample() {
        List challenges = new ArrayList<Challenge>();
        Challenge c1 = new Challenge("Manniquin Challenge","Y_UmWdcTrrc");
        Challenge c2 = new Challenge("Ice bucket Challenge","1KhZKNZO8mQ");
        Challenge c3 = new Challenge("Basketball Challenge","UiLSiqyDf4Y");

        challenges.add(c1);
        challenges.add(c2);
        challenges.add(c3);
        return challenges;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mAdapter.releaseLoaders();
    }

}
