package com.example.android.popular.movie.model.provider;

import com.example.android.popular.movie.model.persistence.MovieDataBaseHelper;
import com.example.android.popular.movie.model.persistence.MovieDataBaseUtils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MovieContentProvider extends ContentProvider {

    /** The authority of this content provider. */
    public static final String AUTHORITY = "com.example.android.popular.movie.provider";

    /** The URI for the movie table. */
    public static final Uri URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/" + MovieDataBaseUtils.TABLE_NAME);

    /** The match code for some items in the movies table. */
    private static final int CODE_MOVIE_DIR = 1;

    /** The match code for an item in the movies table. */
    private static final int CODE_MOVIE_ITEM = 2;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, MovieDataBaseUtils.TABLE_NAME, CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, MovieDataBaseUtils.TABLE_NAME + "/*", CODE_MOVIE_ITEM);
    }


    public MovieContentProvider() {
    }

    @Override
    public boolean onCreate() {
        MovieDataBaseHelper.initializeInstance(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
        case CODE_MOVIE_DIR:
            throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
        case CODE_MOVIE_ITEM:
            int rowsDeleted = 0;
            final Context context = getContext();
            if (context == null) {
                return rowsDeleted;
            }
            SQLiteDatabase sqLiteDatabase = MovieDataBaseHelper.getInstance().openDataBase();
            String id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = sqLiteDatabase.delete(
                        MovieDataBaseUtils.TABLE_NAME,
                        MovieDataBaseUtils.COLUMN_ID + "=" + id,
                        null);
            } else {
                rowsDeleted = sqLiteDatabase.delete(
                        MovieDataBaseUtils.TABLE_NAME,
                        MovieDataBaseUtils.COLUMN_ID  + "=" + id
                                + " and " + selection,
                        selectionArgs);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsDeleted;
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
            SQLiteDatabase sqLiteDatabase = MovieDataBaseHelper.getInstance().openDataBase();
            final long id = sqLiteDatabase.insert(MovieDataBaseUtils.TABLE_NAME, null, values);
            context.getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);
        case CODE_MOVIE_ITEM:
            throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            SQLiteDatabase sqLiteDatabase = MovieDataBaseHelper.getInstance().openDataBase();
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(MovieDataBaseUtils.TABLE_NAME);
            final Cursor cursor;
            if (code == CODE_MOVIE_ITEM) {
                queryBuilder.appendWhere(MovieDataBaseUtils.COLUMN_ID + "=" + uri.getLastPathSegment());

            }

            cursor = queryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
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
            SQLiteDatabase sqLiteDatabase = MovieDataBaseHelper.getInstance().openDataBase();
            String id = uri.getLastPathSegment();
            int rowsUpdated = 0;
            final Context context = getContext();
            if (context == null) {
                return rowsUpdated;
            }
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = sqLiteDatabase.update(MovieDataBaseUtils.TABLE_NAME, values,
                        MovieDataBaseUtils.COLUMN_ID + "=" + id, null);
            } else {
                rowsUpdated = sqLiteDatabase.update(MovieDataBaseUtils.TABLE_NAME, values,
                        MovieDataBaseUtils.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
            }
            context.getContentResolver().notifyChange(uri, null);
            return rowsUpdated;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
