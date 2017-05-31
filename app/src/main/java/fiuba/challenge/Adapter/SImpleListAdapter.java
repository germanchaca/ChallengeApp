package fiuba.challenge.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fiuba.challenge.R;
import fiuba.challenge.model.Challenge;

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.SimpleViewHolder> {
    private List<Challenge> challengeList;

    public SimpleListAdapter(List<Challenge> challenges){
        challengeList = challenges;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_challenge_list_item, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        final Challenge challenge = challengeList.get(position);
        holder.titleTextView.setText(challenge.getTitle());
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        protected TextView titleTextView;

        public SimpleViewHolder(View view) {
            super(view);
            titleTextView = ((TextView)view.findViewById(R.id.openChallegengesTitleTextView));
        }
    }
}
