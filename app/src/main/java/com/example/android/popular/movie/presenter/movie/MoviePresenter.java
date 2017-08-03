package com.example.android.popular.movie.presenter.movie;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public interface MoviePresenter {

    void loadMovies(int option);

    void loadFavoriteMovies();

    void reloadFavoriteMovies(boolean isRestart);

    int getCurrentOption();

}
