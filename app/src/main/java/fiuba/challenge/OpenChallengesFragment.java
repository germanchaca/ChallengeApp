package fiuba.challenge;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;

import java.util.ArrayList;
import java.util.List;

import fiuba.challenge.adapter.OpenChallengesListAdapter;
import fiuba.challenge.model.Challenge;

public class OpenChallengesFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected OpenChallengesListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;


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

        List challenges = new ArrayList<Challenge>();
        Challenge c1 = new Challenge("Manniquin Challenge","Y_UmWdcTrrc");
        Challenge c2 = new Challenge("Ice bucket Challenge","1KhZKNZO8mQ");
        Challenge c3 = new Challenge("Basketball Challenge","UiLSiqyDf4Y");

        challenges.add(c1);
        challenges.add(c2);
        challenges.add(c3);
        mAdapter = new OpenChallengesListAdapter(this.getContext(),challenges);

        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mAdapter.releaseLoaders();
    }

}
