
package com.example.android.popular.movie.view.adapter;

import java.util.List;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.model.entity.Review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * {@link ReviewAdapter} exposes a list of reviews to a
 * {@link RecyclerView}
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private List<Review> reviews;
    private final ReviewAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ReviewAdapterOnClickHandler {
        void onClick(Review review);
    }

    public ReviewAdapter(ReviewAdapterOnClickHandler clickHandler) {
        super();
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a JsonReview list item.
     */
    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final TextView autorTextView;
        final TextView contentTextView;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            autorTextView = (TextView) view.findViewById(R.id.item_review_title_text);
            contentTextView = (TextView) view.findViewById(R.id.item_review_content_text);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Review review = reviews.get(adapterPosition);
            mClickHandler.onClick(review);
        }
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_review;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder videoAdapterViewHolder, int position) {
        Review review = reviews.get(position);
        videoAdapterViewHolder.autorTextView.setText(review.getAuthor());
        videoAdapterViewHolder.contentTextView.setText(review.getContent());
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        if (reviews == null)
            return 0;
        return reviews.size();
    }

    /**
     * set Movies list
     *
     * @param reviews The new movie list.
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}