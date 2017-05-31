package fiuba.challenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fiuba.challenge.ChallengeActivity;
import fiuba.challenge.R;
import fiuba.challenge.model.Challenge;

public class OpenChallengesListAdapter extends RecyclerView.Adapter<OpenChallengesListAdapter.SimpleViewHolder> {
    private List<Challenge> challengeList;

    public OpenChallengesListAdapter(List<Challenge> challenges){
        challengeList = challenges;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_challenge_list_item, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        final Challenge challenge = challengeList.get(position);
        holder.titleTextView.setText(challenge.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, ChallengeActivity.class);
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        protected TextView titleTextView;
        protected View cardView;
        protected final Context context;

        public SimpleViewHolder(View view) {
            super(view);
            context = view.getContext();
            cardView = ((View)view.findViewById(R.id.openChallegengesCardView));
            titleTextView = ((TextView)view.findViewById(R.id.openChallegengesTitleTextView));
        }
    }

}
