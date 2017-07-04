package com.example.android.popular.movie.view.ui;

import java.util.List;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.presenter.MoviePresenter;
import com.example.android.popular.movie.presenter.MoviePresenterImpl;
import com.example.android.popular.movie.presenter.MovieView;
import com.example.android.popular.movie.utils.Constanst;
import com.example.android.popular.movie.utils.DeviceUtils;
import com.example.android.popular.movie.view.adapter.MovieAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * https://www.tutorialspoint.com/android/android_fragments.htm
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment
        implements MovieView, MovieFragmentListener, MovieAdapter.MovieAdapterOnClickHandler {

    private MoviePresenter presenter;
    private ProgressBar loader;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;

    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean isVerticalOrientation = DeviceUtils
                .isVerticalSscreenOrientation(getResources().getConfiguration().orientation);
        int columns = isTablet && !isVerticalOrientation ? 4 : 2;
        loader = (ProgressBar) view.findViewById(R.id.loader_view);
        movieRecyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columns, GridLayoutManager.VERTICAL,
                false);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        movieRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new MoviePresenterImpl(this);
        presenter.loadMovies(false);
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
    public void onMoviesLoaded(final List<Movie> movies) {
        movieRecyclerView.post(new Runnable() {
            @Override
            public void run() {
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
    public void sortMovies(boolean sort) {
        movieAdapter.setMovies(null);
        presenter.loadMovies(sort);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constanst.MOVIE_EXTRA, movie);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(intent);
    }
}
