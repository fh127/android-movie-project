package com.example.android.popular.movie.model.provider;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public class MovieContentProviderHandler extends AsyncQueryHandler {

    private SqLiteListener sqLiteListener;

    public MovieContentProviderHandler(ContentResolver cr, SqLiteListener listener) {
        super(cr);
        this.sqLiteListener = listener;
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        sqLiteListener.onQueryResultTransaction(cursor);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        super.onInsertComplete(token, cookie, uri);
        sqLiteListener.onInsertResultTransaction(uri);
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        super.onDeleteComplete(token, cookie, result);
        sqLiteListener.onDeleteResultTransaction(result);
    }

    public interface SqLiteListener {

        void onQueryResultTransaction(Cursor cursor);

        void onInsertResultTransaction(Uri uri);

        void onDeleteResultTransaction(int result);
    }

}