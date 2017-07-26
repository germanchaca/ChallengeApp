package fiuba.challenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

import fiuba.challenge.ChallengeActivity;
import fiuba.challenge.R;
import fiuba.challenge.model.Proof;
import fiuba.challenge.youtube.ThumbnailListener;
import fiuba.challenge.youtube.Utils;

public class HotProofsListAdapter extends RecyclerView.Adapter<HotProofsListAdapter.SimpleViewHolder> {

    public static final String TAG = "HotProofsListAdapter";

    public static final String PROOF_INTENT = "PROOF_INTENT";
    private List<Proof> proofList;

    private final ThumbnailListener thumbnailListener;

    public HotProofsListAdapter(List<Proof> proofs){
        proofList = proofs;
        thumbnailListener = new ThumbnailListener();

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_proofs_item_list, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(v);
        holder.thumbnail.initialize(Utils.API_KEY, thumbnailListener);

        int parentWidth = parent.getWidth();
        int youtubeThumbnailHeight = (int) (parentWidth * 9 / 16);
        holder.thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.thumbnail.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, youtubeThumbnailHeight));

        return holder;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        final Proof proof = proofList.get(position);
        holder.thumbnail.setTag(proof.getVideoId());

        YouTubeThumbnailLoader loader = thumbnailListener.getThumbnailViewToLoaderMap().get(holder.thumbnail);
        if (loader == null) {
            // 2) The view is already created, and is currently being initialized. We store the
            //    current videoId in the tag.
            holder.thumbnail.setTag(proof.getVideoId());
        } else {
            // 3) The view is already created and already initialized. Simply set the right videoId
            //    on the loader.
            holder.thumbnail.setImageResource(R.drawable.loading_thumbnail);
            loader.setVideo(proof.getVideoId());
        }

        holder.titleTextView.setText(proof.getChallenge().getTitle());
        holder.userNameTextView.setText("Subido por " + proof.getUsername());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, ChallengeActivity.class);
                intent.putExtra(PROOF_INTENT,proof);
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return proofList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private final YouTubeThumbnailView thumbnail;
        protected TextView userNameTextView;
        protected TextView titleTextView;
        protected View cardView;
        protected final Context context;

        public SimpleViewHolder(View view) {
            super(view);
            context = view.getContext();
            cardView = ((View)view.findViewById(R.id.hotProofsItemCardView));
            titleTextView = ((TextView)view.findViewById(R.id.HotProofItemTitleTextView));
            userNameTextView = (TextView) view.findViewById(R.id.username);
            thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnailProof);

        }
    }

}
