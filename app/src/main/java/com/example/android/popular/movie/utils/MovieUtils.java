package com.example.android.popular.movie.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.example.android.popular.movie.R;

import java.util.Collection;

/**
 * Created by fabianantoniohoyospulido on 7/28/17.
 */

public class MovieUtils {

    // key movie option
    public static final int MOST_POPULAR_OPTION = 1;
    public static final int HIGHT_RATED_OPTION = 2;
    public static final int FAVORITE_OPTION = 3;

    public static final SparseIntArray movieMenuUiOption = new SparseIntArray();
    static {
        movieMenuUiOption.put(R.id.action_favorites, FAVORITE_OPTION);
        movieMenuUiOption.put(R.id.action_top_rated_movies, HIGHT_RATED_OPTION);
        movieMenuUiOption.put(R.id.action_popular_movies, MOST_POPULAR_OPTION);
    }

    public static final SparseBooleanArray movieSortOption = new SparseBooleanArray();
    static {
        movieSortOption.put(HIGHT_RATED_OPTION, true);
        movieSortOption.put(MOST_POPULAR_OPTION, false);
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();

    }

    public static boolean isFavoriteMovieOption(int option) {
        return FAVORITE_OPTION == option;
    }

    public static void playVideo(Context context, String key) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key));
        }
        context.startActivity(intent);
    }
}
