package fiuba.challenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.Collections;
import java.util.List;

import fiuba.challenge.adapter.HotProofsListAdapter;
import fiuba.challenge.model.Challenge;
import fiuba.challenge.model.Proof;

public class MyVideosFragment extends Fragment {
    public static final String TAG = "MyVideosFragment";

    protected RecyclerView mRecyclerView;
    protected HotProofsListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    private String FIREBASE_URL = "https://challenge-6f44d.firebaseio.com/";
    private String FIREBASE_CHILD = "challenges";

    Firebase firebase;

    public MyVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_hot_proofs, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.hotProofsRecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        final List<Proof> finalProofList = new ArrayList<Proof>();

        SharedPreferences settings = getContext().getSharedPreferences(LoginActivity.APP, Context.MODE_PRIVATE);
        final String myUser = settings.getString(LoginActivity.PREF_ACCOUNT_NAME, null);

        Firebase.setAndroidContext(getContext());

        firebase = new Firebase(FIREBASE_URL).child(FIREBASE_CHILD);
        firebase.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot snapshot) {

                finalProofList.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    String challengeId = data.getKey();
                    //Log.d(TAG, "challengeId: " + challengeId);

                    Challenge challenge = new Challenge();
                    challenge.setId(challengeId);

                    for (DataSnapshot challengeSnapshot : data.getChildren()) {
                        switch (challengeSnapshot.getKey().toString()){
                            case "creationDate":
                                Long creationDate = Long.parseLong(challengeSnapshot.getValue().toString());
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
                                                Long proofCreationDate =  Long.parseLong(proofSnapshot.getValue().toString());
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
                                    }
                                    if(myUser.equals(proof.getUsername())) finalProofList.add(proof);
                                    challenge.addProof(proof);
                                }
                                break;
                        }
                    }
                }
                Log.d(TAG,String.valueOf(finalProofList.size()));

                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(FirebaseError error) {
            }

        });

        mAdapter = new HotProofsListAdapter(finalProofList);

        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
