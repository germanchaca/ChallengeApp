package fiuba.challenge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

import fiuba.challenge.R;
import fiuba.challenge.model.Proof;
import fiuba.challenge.youtube.ThumbnailListener;
import fiuba.challenge.youtube.Utils;

public class GridListAdapter extends RecyclerView.Adapter<GridListAdapter.SimpleViewHolder> {
    private final ThumbnailListener thumbnailListener;
    private List<Proof> proofList;
    private String videoId;
    private YouTubePlayer player;


    public void setPlayer(YouTubePlayer player) {
        this.player = player;
    }


    public GridListAdapter(List<Proof> proofs, String videoId){
        thumbnailListener = new ThumbnailListener();
        proofList = proofs;
        this.videoId = videoId;
    }

    public void setVideoId(String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            if(this.player != null){
                player.loadVideo(videoId);
            }
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int type) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_proofs_list_item, parent, false);
        SimpleViewHolder holder = new SimpleViewHolder(v);
        holder.thumbnail.initialize(Utils.API_KEY, thumbnailListener);

        holder.thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.thumbnail.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
        holder.userNameTextView.setText(proof.getUsername());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVideoId(proof.getVideoId() );
            }
        });
    }

    @Override
    public int getItemCount() {
        return proofList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        protected TextView userNameTextView;
        private final YouTubeThumbnailView thumbnail;
        protected View cardView;
        protected final Context context;

        public SimpleViewHolder(View view) {
            super(view);
            context = view.getContext();
            thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnailProof);
            cardView = ((View)view.findViewById(R.id.proofsCardView));
            userNameTextView = ((TextView)view.findViewById(R.id.challengeProofUserNameTextView));
        }
    }
    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : this.thumbnailListener.getThumbnailViewToLoaderMap().values()) {
            loader.release();
        }
    }
}
