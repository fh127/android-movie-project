package com.example.android.popular.movie.model.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.persistence.MovieDao;
import com.example.android.popular.movie.model.persistence.MovieDataBaseHelper;
import com.example.android.popular.movie.model.persistence.MovieDatabase;

public class MovieContentProvider extends ContentProvider {

    /** The authority of this content provider. */
    public static final String AUTHORITY = "com.example.android.popular.movie.provider";

    /** The URI for the movie table. */
    public static final Uri URI_MOVIE = Uri
            .parse("content://" + AUTHORITY + "/" + MovieDataBaseHelper.TABLE_NAME);

    /** The match code for some items in the movies table. */
    private static final int CODE_MOVIE_DIR = 1;

    /** The match code for an item in the movies table. */
    private static final int CODE_MOVIE_ITEM = 2;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, MovieDataBaseHelper.TABLE_NAME, CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, MovieDataBaseHelper.TABLE_NAME + "/*", CODE_MOVIE_ITEM);
    }

    public MovieContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
        case CODE_MOVIE_DIR:
            throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
        case CODE_MOVIE_ITEM:
            final Context context = getContext();
            if (context == null) {
                return 0;
            }
            final int count = MovieDatabase.getInstance(context).movieDao()
                    .deleteMovieById(ContentUris.parseId(uri));
            context.getContentResolver().notifyChange(uri, null);
            return count;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (MATCHER.match(uri)) {
        case CODE_MOVIE_DIR:
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            final long id = MovieDatabase.getInstance(context).movieDao()
                    .insertMovie(MovieDataBaseHelper.fromContentValues(values));
            context.getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);
        case CODE_MOVIE_ITEM:
            throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            MovieDao movieDao = MovieDatabase.getInstance(context).movieDao();
            final Cursor cursor;
            if (code == CODE_MOVIE_DIR) {
                cursor = movieDao.selectAll();
            } else {
                cursor = movieDao.findMovieById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
        case CODE_MOVIE_DIR:
            throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
        case CODE_MOVIE_ITEM:
            final Context context = getContext();
            if (context == null) {
                return 0;
            }
            final Movie movie = MovieDataBaseHelper.fromContentValues(values);
            final int count = MovieDatabase.getInstance(context).movieDao().update(movie);
            context.getContentResolver().notifyChange(uri, null);
            return count;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}