package com.example.android.popular.movie.presenter;

import static com.example.android.popular.movie.model.services.MovieRequestIntentService.MOVIE_RESULT;
import static com.example.android.popular.movie.model.services.MovieRequestIntentService.MOVIE_RESULT_CODE;
import static com.example.android.popular.movie.presenter.QueryOption.QUERY_ALL;
import static com.example.android.popular.movie.presenter.QueryOption.QUERY_FIND_BY_ID;
import static com.example.android.popular.movie.presenter.QueryOption.QUERY_FIND_ID;
import java.util.ArrayList;
import java.util.List;
import com.example.android.popular.movie.app.MovieApplication;
import com.example.android.popular.movie.model.api.request.MovieRequestParameter;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.entity.MovieParcelable;
import com.example.android.popular.movie.model.persistence.MovieDataBaseHelper;
import com.example.android.popular.movie.model.provider.MovieContentProvider;
import com.example.android.popular.movie.model.provider.MovieContentProviderHandler;
import com.example.android.popular.movie.model.services.MovieReceiver;
import com.example.android.popular.movie.model.services.MovieRequestIntentService;
import com.example.android.popular.movie.utils.MovieUtils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public class MovieInteractorImpl
        implements MovieInteractor, MovieReceiver.Receiver, MovieContentProviderHandler.SqLiteListener {

    private final MovieContentProviderHandler contentProviderHandlerReference;
    private @QueryOption.QUERYOPTION int queryOption;
    private OnResponseListener listener;
    private Movie movie;

    public MovieInteractorImpl() {
        contentProviderHandlerReference = new MovieContentProviderHandler(
                MovieApplication.getInstance().getContext().getContentResolver(), this);
    }

    @Override
    public void processRequest(String action, MovieRequestParameter parameter, OnResponseListener listener) {
        this.listener = listener;
        Context context = MovieApplication.getInstance().getContext();
        MovieReceiver receiver = new MovieReceiver(new Handler());
        receiver.setReceiver(this);
        Intent intent = MovieRequestIntentService.createIntent(context, action, parameter, receiver);
        context.startService(intent);
    }

    @Override
    public void addOrRemoveMovieToFavorites(final Movie movie, OnResponseListener listener) {
        this.listener = listener;
        this.movie = movie;
        Uri uri = MovieDataBaseHelper.getUriWithId(movie.getId());
        queryOption = QUERY_FIND_ID;
        contentProviderHandlerReference.startQuery(1, null, uri, null, null, null, null);
    }

    @Override
    public void loadFavoriteMovies(Activity activity, OnResponseListener listener) {
        this.listener = listener;
        queryOption = QUERY_ALL;
        contentProviderHandlerReference.startQuery(1, null, MovieContentProvider.URI_MOVIE, null, null, null,
                MovieDataBaseHelper.COLUMN_TITLE);
    }

    @Override
    public void findFavoriteMovie(final long movieId, final OnResponseListener listener) {
        this.listener = listener;
        Uri uri = MovieDataBaseHelper.getUriWithId(movieId);
        queryOption = QUERY_FIND_BY_ID;
        contentProviderHandlerReference.startQuery(1, null, uri, null, null, null, null);
    }

    private void addMovieToFavorites(final Movie movie) {
        ContentValues values = MovieDataBaseHelper.getContentValues(movie);
        contentProviderHandlerReference.startInsert(1, null, MovieContentProvider.URI_MOVIE, values);
    }

    private void removeMovieToFavorites(final Movie movie) {
        Uri uriForDelete = MovieDataBaseHelper.getUriWithId(movie.getId());
        contentProviderHandlerReference.startDelete(1, null, uriForDelete, null, null);

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
        case MOVIE_RESULT_CODE:
            List<MovieParcelable> parcelableArrayList = resultData.getParcelableArrayList(MOVIE_RESULT);
            if (MovieUtils.isNotEmpty(parcelableArrayList)) {
                listener.onResponseSuccess(parcelableArrayList);
            } else {
                listener.onResponseError();
            }
            break;
        default:
            listener.onResponseError();
            break;
        }
    }

    private void loadFavoriteMoviesFinished(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        try {
            if (cursor != null) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    movies.add(MovieDataBaseHelper.fromContentValues(cursor));
                }
                if (!cursor.isClosed())
                    cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener.onFavoriteMovies(movies);
    }

    @Override
    public void onQueryResultTransaction(Cursor cursor) {
        if (queryOption == QUERY_ALL) {
            loadFavoriteMoviesFinished(cursor);
        } else if (queryOption == QUERY_FIND_ID) {
            handleMovieItem(cursor);
        } else if (queryOption == QUERY_FIND_BY_ID) {
            boolean itemAdded = cursor != null && cursor.getCount() > 0;
            listener.updateFavoriteStateUI(itemAdded);
            if (itemAdded && !cursor.isClosed())
                cursor.close();
        }
    }

    private void handleMovieItem(Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            removeMovieToFavorites(movie);
            if (!cursor.isClosed())
                cursor.close();
        } else {
            addMovieToFavorites(movie);
        }

    }

    @Override
    public void onInsertResultTransaction(Uri uri) {
        if (uri != null) {
            listener.onMovieAdded();
        } else {
            listener.onErrorMovieFavoriteList();
        }
    }

    @Override
    public void onDeleteResultTransaction(int result) {
        if (result > 0) {
            listener.onMovieRemoved();
        } else {
            listener.onErrorMovieFavoriteList();
        }
    }
}
