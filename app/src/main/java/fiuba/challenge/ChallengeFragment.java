package fiuba.challenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fiuba.challenge.adapter.GridListAdapter;
import fiuba.challenge.adapter.SimpleListAdapter;
import fiuba.challenge.model.Challenge;
import fiuba.challenge.model.Proof;
import fiuba.challenge.model.User;

public class ChallengeFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected GridListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected TextView challengeTitleTextView;

    protected static int GRID_COLS = 2;

    public ChallengeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenge, container, false);

        challengeTitleTextView = (TextView) rootView.findViewById(R.id.challengeTitleTextView);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.openChallengesRecyclerView);

        mLayoutManager = new GridLayoutManager(getActivity(),GRID_COLS);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List proofs = new ArrayList<Proof>();
        Proof p1 = new Proof("",new User("Charlie"));
        Proof p2 = new Proof("",new User("Sam"));
        Proof p3 = new Proof("",new User("Joe"));
        Proof p4 = new Proof("",new User("Santi"));

        proofs.add(p1);
        proofs.add(p2);
        proofs.add(p3);
        proofs.add(p4);

        mAdapter = new GridListAdapter(proofs);

        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


}
