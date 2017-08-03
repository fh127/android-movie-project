package com.example.android.popular.movie.model.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.android.popular.movie.model.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);

    @Query("SELECT * FROM movies")
    List<Movie> getMovies();

    @Query("SELECT * FROM movies")
    Cursor selectAll();

    @Query("SELECT * FROM " + MovieDataBaseHelper.TABLE_NAME + " WHERE " + MovieDataBaseHelper.COLUMN_ID + " = :id")
    Cursor findMovieById(long id);

    @Delete
    int deleteMovies(Movie... movies);

    @Query("DELETE FROM " + MovieDataBaseHelper.TABLE_NAME + " WHERE " + MovieDataBaseHelper.COLUMN_ID + " = :id")
    int deleteMovieById(long id);

    @Update
    int update(Movie movie);

}