package com.example.android.popular.movie.model.persistence;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.provider.MovieContentProvider;

/**
 * Created by fabianantoniohoyospulido on 7/31/17.
 */

public class MovieDataBaseUtils {
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
    public static final String COLUMN_BACKDROP_PATH = "backdropPath";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "releaseDate";
    public static final String COLUMN_PREFERED = "prefered";


    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " text not null, "
            + COLUMN_VOTE_AVERAGE + " text not null, "
            + COLUMN_TITLE+ " text not null, "
            + COLUMN_BACKDROP_PATH+ " text not null, "
            + COLUMN_OVERVIEW+ " text not null, "
            + COLUMN_RELEASE_DATE+ " text not null, "
            + COLUMN_PREFERED+ " text not null, "
            + COLUMN_ORIGINAL_TITLE
            + " text not null"
            + ");";




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
        values.put(MovieDataBaseUtils.COLUMN_ID, movie.getId());
        values.put(MovieDataBaseUtils.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(MovieDataBaseUtils.COLUMN_TITLE, movie.getTitle());
        values.put(MovieDataBaseUtils.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        values.put(MovieDataBaseUtils.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieDataBaseUtils.COLUMN_PREFERED, movie.isPrefered());
        values.put(MovieDataBaseUtils.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieDataBaseUtils.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        return values;
    }

    public static Uri getUriWithId(long id) {
        return ContentUris.withAppendedId(MovieContentProvider.URI_MOVIE, id);
    }



    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(TABLE_NAME, "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
