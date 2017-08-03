package com.example.android.popular.movie.presenter;

import java.util.List;

import com.example.android.popular.movie.model.api.request.MovieRequestParameter;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.entity.MovieParcelable;
import com.example.android.popular.movie.model.entity.Review;
import com.example.android.popular.movie.model.entity.Video;

import android.app.Activity;
import android.content.Context;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public interface MovieInteractor {

    interface OnResponseListener {

        void onResponseError();

        void onResponseSuccess(List<MovieParcelable> movieParcelables);

        void onMovieAdded();

        void onMovieRemoved();

        void onErrorMovieFavoriteList();

        void onFavoriteMovies(List<Movie> movies);

        void updateFavoriteStateUI(boolean added);
    }

    void processRequest(String action, MovieRequestParameter parameter, OnResponseListener listener);

    void addOrRemoveMovieToFavorites(Movie movie, OnResponseListener listener);

    void loadFavoriteMovies(Activity activity, OnResponseListener listener);

    void findFavoriteMovie(long movieId, OnResponseListener listener);

}
