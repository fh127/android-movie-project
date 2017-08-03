package com.example.android.popular.movie.presenter.moviedetail;

import com.example.android.popular.movie.model.entity.Movie;

/**
 * Created by fabianantoniohoyospulido on 7/29/17.
 */

public interface MovieDetailPresenter {

     void loadDetails(int movieId);

     void handleFavoriteMovies(Movie movie);

     int getCurrentOption();

     void updatFavoriteState(long movieId);
}
