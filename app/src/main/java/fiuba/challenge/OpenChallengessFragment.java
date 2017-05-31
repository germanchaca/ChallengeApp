package fiuba.challenge;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fiuba.challenge.adapter.SimpleListAdapter;
import fiuba.challenge.model.Challenge;

public class OpenChallengessFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected SimpleListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    public OpenChallengessFragment() {
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
        Challenge c1 = new Challenge("Manniquin Challenge",null,"");
        Challenge c2 = new Challenge("Ice bucket Challenge",null,"");
        Challenge c3 = new Challenge("Basketball Challenge",null,"");
        challenges.add(c1);
        challenges.add(c2);
        challenges.add(c3);
        mAdapter = new SimpleListAdapter(challenges);

        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


}
