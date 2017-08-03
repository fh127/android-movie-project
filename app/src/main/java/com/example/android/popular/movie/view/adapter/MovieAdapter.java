
package com.example.android.popular.movie.view.adapter;

import java.util.List;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.model.api.MovieApi;
import com.example.android.popular.movie.model.entity.Movie;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * {@link MovieAdapter} exposes a list of movies to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> movies;
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        super();
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a JsonMovie list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public final ImageView imageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.poster_item);
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
            Movie movie = movies.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_poster;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movie = movies.get(position);
        String imageUrl = MovieApi.getImageUri(movie.getBackdropPath());
        Context context = movieAdapterViewHolder.imageView.getContext();
        Picasso.with(context)
                .load(imageUrl)
                .error(android.R.drawable.stat_notify_error)
                .placeholder(android.R.drawable.stat_sys_download)
                .into(movieAdapterViewHolder.imageView);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        if (movies == null)
            return 0;
        return movies.size();
    }

    /**
     * set Movies list
     *
     * @param movies The new movie list.
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}