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
import fiuba.challenge.model.Challenge;
import fiuba.challenge.youtube.ThumbnailListener;
import fiuba.challenge.youtube.Utils;

public class OpenChallengesListAdapter extends RecyclerView.Adapter<OpenChallengesListAdapter.SimpleViewHolder> {
    public static final String CHALLENGE_INTENT = "challenge";
    private final ThumbnailListener thumbnailListener;
    private List<Challenge> challengeList;

    public OpenChallengesListAdapter(Context context,List<Challenge> challenges){
        challengeList = challenges;
        thumbnailListener = new ThumbnailListener();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_challenge_list_item, parent, false);
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
        final Challenge challenge = challengeList.get(position);

        holder.thumbnail.setTag(challenge.getRulesVideoId());

        YouTubeThumbnailLoader loader = thumbnailListener.getThumbnailViewToLoaderMap().get(holder.thumbnail);
        if (loader == null) {
            // 2) The view is already created, and is currently being initialized. We store the
            //    current videoId in the tag.
            holder.thumbnail.setTag(challenge.getRulesVideoId());
        } else {
            // 3) The view is already created and already initialized. Simply set the right videoId
            //    on the loader.
            holder.thumbnail.setImageResource(R.drawable.loading_thumbnail);
            loader.setVideo(challenge.getRulesVideoId());
        }

        holder.titleTextView.setText(challenge.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, ChallengeActivity.class);
                intent.putExtra(CHALLENGE_INTENT,challenge);

                holder.context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private final YouTubeThumbnailView thumbnail;
        protected TextView titleTextView;
        protected View cardView;
        protected final Context context;

        public SimpleViewHolder(View view) {
            super(view);
            context = view.getContext();
            cardView = ((View)view.findViewById(R.id.openChallegengesCardView));
            titleTextView = ((TextView)view.findViewById(R.id.openChallegengesTitleTextView));
            thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
        }
    }
    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : this.thumbnailListener.getThumbnailViewToLoaderMap().values()) {
            loader.release();
        }
    }
}
