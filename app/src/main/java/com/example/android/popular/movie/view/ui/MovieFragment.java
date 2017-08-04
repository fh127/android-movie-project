package com.example.android.popular.movie.view.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.presenter.movie.MoviePresenter;
import com.example.android.popular.movie.presenter.movie.MoviePresenterImpl;
import com.example.android.popular.movie.presenter.movie.MovieView;
import com.example.android.popular.movie.utils.Constants;
import com.example.android.popular.movie.utils.DeviceUtils;
import com.example.android.popular.movie.utils.MovieUtils;
import com.example.android.popular.movie.view.adapter.MovieAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment
        implements MovieView, MovieFragmentListener, MovieAdapter.MovieAdapterOnClickHandler {

    private static final int FROM_FAVORITE_REQUEST = 105;
    private MoviePresenter presenter;
    private ProgressBar loader;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movies;
    private boolean isShouldLoadFavoriteProvider;

    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        presenter = new MoviePresenterImpl(this);
        initUI(view);
        return view;
    }

    private void restoreSate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList(Constants.MOVIES_KEY);
            showMovies(movies);
        } else {
            int option = presenter.getCurrentOption();
            if(!MovieUtils.isFavoriteMovieOption(option)) {
                sortMovies(option != -1 ? option : MovieUtils.MOST_POPULAR_OPTION);
            }else{
                isShouldLoadFavoriteProvider =true;
            }
        }
    }

    private void initUI(View view) {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean isVerticalOrientation = DeviceUtils
                .isVerticalSscreenOrientation(getResources().getConfiguration().orientation);
        int columns = isTablet && !isVerticalOrientation ? 4 : 2;
        loader = (ProgressBar) view.findViewById(R.id.loader_view);
        loader.setVisibility(View.GONE);
        movieRecyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columns, GridLayoutManager.VERTICAL,
                false);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        movieRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreSate(savedInstanceState);

    }

    @Override
    public void onShowLoader(final boolean show) {
        loader.post(new Runnable() {
            @Override
            public void run() {
                loader.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (MovieUtils.isFavoriteMovieOption(presenter.getCurrentOption()) && isShouldLoadFavoriteProvider) {
            showMovies(new ArrayList<Movie>());
            presenter.loadFavoriteMovies();
            isShouldLoadFavoriteProvider = false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FROM_FAVORITE_REQUEST) {
            isShouldLoadFavoriteProvider = true;
        }
    }

    @Override
    public void onMoviesLoaded(final List<Movie> movies) {
        showMovies(movies);
    }

    private void showMovies(final List<Movie> movies) {
        this.movies = movies;
        movieRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                onShowLoader(false);
                movieAdapter.setMovies(movies);
            }
        });
    }

    @Override
    public void ShowErrorMessage(String message) {
        if (getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
    }

    @Override
    public Context getContextReference() {
        return getContext();
    }

    @Override
    public void sortMovies(int option) {
        movieAdapter.setMovies(null);
        presenter.loadMovies(option);
    }

    @Override
    public void onClick(Movie jsonMovie) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.MOVIE_EXTRA, jsonMovie);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (MovieUtils.isFavoriteMovieOption(presenter.getCurrentOption())) {
            startActivityForResult(intent, FROM_FAVORITE_REQUEST);
        } else {
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null)
            outState.putParcelableArrayList(Constants.MOVIES_KEY, (ArrayList<? extends Parcelable>) movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Activity getActivityReference() {
        return getActivity();
    }

}
