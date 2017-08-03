package com.example.android.popular.movie.model.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.example.android.popular.movie.model.entity.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    /**
     * @return The DAO for the Movie table.
     */
    @SuppressWarnings("WeakerAccess")
    public abstract MovieDao movieDao();

    /** The only instance */
    private static MovieDatabase sInstance;

    /**
     * Gets the singleton instance of MovieDatabase.
     *
     * @param context The context.
     * @return The singleton instance of MovieDatabase.
     */
    public static synchronized MovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movie.db")
                    .build();
        }
        return sInstance;
    }
}