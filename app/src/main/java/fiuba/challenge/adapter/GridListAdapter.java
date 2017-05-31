package fiuba.challenge.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fiuba.challenge.R;
import fiuba.challenge.model.Proof;

public class GridListAdapter extends RecyclerView.Adapter<GridListAdapter.SimpleViewHolder> {
    private List<Proof> proofList;

    public GridListAdapter(List<Proof> proofs){
        proofList = proofs;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_proofs_list_item, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        final Proof proof = proofList.get(position);
        holder.userNameTextView.setText(proof.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return proofList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        protected TextView userNameTextView;

        public SimpleViewHolder(View view) {
            super(view);

            userNameTextView = ((TextView)view.findViewById(R.id.challengeProofUserNameTextView));
        }
    }
}
