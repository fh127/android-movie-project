package com.example.android.popular.movie.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieDataBaseHelper extends SQLiteOpenHelper {

    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDatabase;
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    private static MovieDataBaseHelper instance;

    private MovieDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new MovieDataBaseHelper(context);
        }
    }

    public static MovieDataBaseHelper getInstance() {
        if (instance == null) {
            throw new IllegalStateException(MovieDataBaseHelper.class.getSimpleName()
                    + " is not initialized, call initializeInstance(Context context) method first.");
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MovieDataBaseUtils.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MovieDataBaseUtils.onUpgrade(db, oldVersion, newVersion);
    }

    public synchronized SQLiteDatabase openDataBase() {
        if(mOpenCounter.incrementAndGet() == 1) {
            mDatabase = getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDataBase() {
        if(mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();

        }
    }
}