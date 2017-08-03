package com.example.android.popular.movie.model.persistence;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.provider.MovieContentProvider;

/**
 * Created by fabianantoniohoyospulido on 7/31/17.
 */

public class MovieDataBaseHelper {
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
    public static final String COLUMN_BACKDROP_PATH = "backdropPath";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "releaseDate";
    public static final String COLUMN_PREFERED = "prefered";

    public static Movie fromContentValues(ContentValues values) {
        final Movie movie = new Movie();
        if (values.containsKey(COLUMN_ID))
            movie.setId(values.getAsInteger(COLUMN_ID));
        if (values.containsKey(COLUMN_BACKDROP_PATH))
            movie.setBackdropPath(values.getAsString(COLUMN_BACKDROP_PATH));
        if (values.containsKey(COLUMN_VOTE_AVERAGE))
            movie.setVoteAverage(values.getAsDouble(COLUMN_VOTE_AVERAGE));
        if (values.containsKey(COLUMN_RELEASE_DATE))
            movie.setReleaseDate(values.getAsString(COLUMN_RELEASE_DATE));
        if (values.containsKey(COLUMN_OVERVIEW))
            movie.setOverview(values.getAsString(COLUMN_OVERVIEW));
        if (values.containsKey(COLUMN_TITLE))
            movie.setTitle(values.getAsString(COLUMN_TITLE));
        if (values.containsKey(COLUMN_ORIGINAL_TITLE))
            movie.setOriginalTitle(values.getAsString(COLUMN_ORIGINAL_TITLE));
        if (values.containsKey(COLUMN_PREFERED))
            movie.setPrefered(values.getAsBoolean(COLUMN_PREFERED));
        return movie;
    }

    public static Movie fromContentValues(Cursor values) {
        final Movie movie = new Movie();
        movie.setId(values.getInt(values.getColumnIndexOrThrow(COLUMN_ID)));
        movie.setBackdropPath(values.getString(values.getColumnIndexOrThrow(COLUMN_BACKDROP_PATH)));
        movie.setVoteAverage(values.getDouble(values.getColumnIndexOrThrow(COLUMN_VOTE_AVERAGE)));
        movie.setReleaseDate(values.getString(values.getColumnIndexOrThrow(COLUMN_RELEASE_DATE)));
        movie.setOverview(values.getString(values.getColumnIndexOrThrow(COLUMN_OVERVIEW)));
        movie.setTitle(values.getString(values.getColumnIndexOrThrow(COLUMN_TITLE)));
        movie.setOriginalTitle(values.getString(values.getColumnIndexOrThrow(COLUMN_ORIGINAL_TITLE)));
        movie.setPrefered(true);
        return movie;
    }

    public static ContentValues getContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieDataBaseHelper.COLUMN_ID, movie.getId());
        values.put(MovieDataBaseHelper.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(MovieDataBaseHelper.COLUMN_TITLE, movie.getTitle());
        values.put(MovieDataBaseHelper.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        values.put(MovieDataBaseHelper.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieDataBaseHelper.COLUMN_PREFERED, movie.isPrefered());
        values.put(MovieDataBaseHelper.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieDataBaseHelper.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        return values;
    }

    public static Uri getUriWithId(long id) {
        return ContentUris.withAppendedId(MovieContentProvider.URI_MOVIE, id);
    }
}
