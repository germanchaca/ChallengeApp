package fiuba.challenge;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fiuba.challenge.adapter.GridListAdapter;
import fiuba.challenge.model.Proof;
import fiuba.challenge.model.User;

public class ChallengeActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    protected GridListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected TextView challengeTitleTextView;

    protected static int GRID_COLS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        challengeTitleTextView = (TextView) findViewById(R.id.challengeTitleTextView);

        mRecyclerView = (RecyclerView) findViewById(R.id.openChallengesRecyclerView);

        mLayoutManager = new GridLayoutManager(this,GRID_COLS);
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
        proofs.add(p1);
        proofs.add(p2);
        proofs.add(p3);
        proofs.add(p4);


        mAdapter = new GridListAdapter(proofs);

        mRecyclerView.setAdapter(mAdapter);

    }

}
