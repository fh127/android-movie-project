package com.example.android.popular.movie.presenter.movie;

import static com.example.android.popular.movie.model.api.request.MovieRequestFactory.MOVIE_SORT_LIST_ACTION;
import static com.example.android.popular.movie.utils.MovieUtils.FAVORITE_OPTION;

import java.util.ArrayList;
import java.util.List;

import com.example.android.popular.movie.model.api.request.MovieRequestParameter;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.entity.MovieParcelable;
import com.example.android.popular.movie.presenter.MovieInteractor;
import com.example.android.popular.movie.presenter.MovieInteractorImpl;
import com.example.android.popular.movie.utils.Constants;
import com.example.android.popular.movie.utils.MovieUtils;
import com.example.android.popular.movie.utils.NetworkUtils;
import com.example.android.popular.movie.utils.PreferenceUtils;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public class MoviePresenterImpl implements MoviePresenter, MovieInteractor.OnResponseListener {

    private MovieView view;
    private MovieInteractor interactor;

    public MoviePresenterImpl(MovieView view) {
        this.view = view;
        this.interactor = new MovieInteractorImpl();
    }

    @Override
    public void loadMovies(int option) {
        if (FAVORITE_OPTION == option) {
            loadFavoriteMovies();
        } else if (NetworkUtils.isNetworkConnection()) {
            view.onShowLoader(true);
            processMovieOption(option);
        } else {
            view.ShowErrorMessage(Constants.ERROR_MESSAGE_CONNECTION);
        }
    }

    @Override
    public void loadFavoriteMovies() {
        interactor.loadFavoriteMovies(view.getActivityReference(), this);
    }

    @Override
    public void reloadFavoriteMovies(boolean isRestart) {
        if (isRestart) {
            loadFavoriteMovies();
        }
    }

    @Override
    public int getCurrentOption() {
        return PreferenceUtils.getInstance().getIntValue(Constants.MENU_ITEM_ID);
    }

    private void processMovieOption(int option) {
        MovieRequestParameter.Builder builder = new MovieRequestParameter.Builder();
        builder.sort(MovieUtils.movieSortOption.get(option));
        interactor.processRequest(MOVIE_SORT_LIST_ACTION, builder.build(), this);
    }

    @Override
    public void onResponseError() {
        view.onShowLoader(false);
        view.ShowErrorMessage(Constants.ERROR_MESSAGE_MOVIES);
    }

    @Override
    public void onResponseSuccess(List<MovieParcelable> parcelabeList) {
        view.onShowLoader(false);
        List<Movie> movies = new ArrayList<>();
        for (MovieParcelable parcelable : parcelabeList) {
            movies.add(parcelable.getMovie());
        }
        view.onMoviesLoaded(movies);
    }

    @Override
    public void onMovieAdded() {

    }

    @Override
    public void onMovieRemoved() {

    }

    @Override
    public void onErrorMovieFavoriteList() {
    }

    @Override
    public void onFavoriteMovies(List<Movie> movies) {
        if (MovieUtils.isNotEmpty(movies)) {
            view.onMoviesLoaded(movies);
        } else {
            view.ShowErrorMessage(Constants.ERROR_MESSAGE_MOVIE_FAVORITE_LIST);
        }
    }

    @Override
    public void updateFavoriteStateUI(boolean added) {

    }

}
