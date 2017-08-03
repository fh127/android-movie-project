
package com.example.android.popular.movie.view.adapter;

import java.util.List;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.model.entity.Video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * {@link VideoAdapter} exposes a list of videos to a
 * {@link RecyclerView}
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder> {

    private List<Video> videos;
    private final VideoAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface VideoAdapterOnClickHandler {
        void onClick(Video video);
    }

    public VideoAdapter(VideoAdapterOnClickHandler clickHandler) {
        super();
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a JsonReview list item.
     */
    class VideoAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final ImageView imageView;
        final TextView textView;

        public VideoAdapterViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.play_action_image_view);
            textView = (TextView) view.findViewById(R.id.item_video_name_text);
            textView.setSelected(true);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Video video = videos.get(adapterPosition);
            mClickHandler.onClick(video);
        }
    }

    @Override
    public VideoAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_video;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapterViewHolder videoAdapterViewHolder, int position) {
        Video video = videos.get(position);
        videoAdapterViewHolder.textView.setText(video.getName());
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        if (videos == null)
            return 0;
        return videos.size();
    }

    /**
     * set Movies list
     *
     * @param videos The new movie list.
     */
    public void setVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }
}