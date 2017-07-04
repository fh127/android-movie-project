package com.example.android.popular.movie.presenter;

import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.utils.Constanst;
import com.example.android.popular.movie.utils.NetworkUtils;

import java.util.List;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public class MoviePresenterImpl implements MoviePresenter, MovieInteractor.OnResponseListener {

    private MovieView view;

    public MoviePresenterImpl(MovieView view) {
        this.view = view;
    }

    @Override
    public void loadMovies(boolean sort) {
        if (NetworkUtils.isNetworkAvailable(view.getContextReference())) {
            view.onShowLoader(true);
            MovieInteractor interactor = new MovieInteractorImpl();
            interactor.getMovies(sort, this);
        } else {
            view.ShowErrorMessage(Constanst.ErrorMessageConnection);
        }
    }

    @Override
    public void onResponseError() {
        view.onShowLoader(false);
        view.ShowErrorMessage(Constanst.ErrorMessageMovies);
    }

    @Override
    public void onResponseSuccess(List<Movie> movieList) {
        view.onShowLoader(false);
        view.onMoviesLoaded(movieList);
    }
}
