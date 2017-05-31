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

import fiuba.challenge.adapter.HotProofsListAdapter;
import fiuba.challenge.adapter.OpenChallengesListAdapter;
import fiuba.challenge.model.Challenge;
import fiuba.challenge.model.Proof;
import fiuba.challenge.model.User;

public class HotProofsFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected HotProofsListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    public HotProofsFragment() {
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

        List proofs = new ArrayList<Proof>();
        Challenge c1 = new Challenge("Manniquin Challenge",null,"");
        Challenge c2 = new Challenge("Ice bucket Challenge",null,"");
        Challenge c3 = new Challenge("Basketball Challenge",null,"");

        Proof p1 = new Proof("", new User("Alex"),c1);
        Proof p2 = new Proof("", new User("Sam"),c2);
        Proof p3 = new Proof("", new User("Jollie"),c3);
        Proof p4 = new Proof("", new User("Fede"),c1);
        Proof p5 = new Proof("", new User("Pablo"),c2);
        Proof p6 = new Proof("", new User("Ger"),c3);
        proofs.add(p1);
        proofs.add(p2);
        proofs.add(p3);
        proofs.add(p4);
        proofs.add(p5);
        proofs.add(p6);
        mAdapter = new HotProofsListAdapter(proofs);

        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
