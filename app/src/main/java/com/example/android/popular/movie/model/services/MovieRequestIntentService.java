package com.example.android.popular.movie.model.services;

import java.util.ArrayList;

import com.example.android.popular.movie.model.api.request.MovieRequest;
import com.example.android.popular.movie.model.api.request.MovieRequestFactory;
import com.example.android.popular.movie.model.api.request.MovieRequestParameter;
import com.example.android.popular.movie.model.entity.MovieParcelable;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 *
 */
public class MovieRequestIntentService extends IntentService {

    public static final String MOVIE_REQUEST_PARAMETER = "movie_request_parameter";
    public static final String MOVIE_RECEIVER = "movie_receiver_parameter";
    public static final String MOVIE_RESULT = "movie_result";
    public static final int MOVIE_RESULT_CODE = 101;

    public static Intent createIntent(Context context, String action, MovieRequestParameter parameter,
            MovieReceiver receiver) {
        Intent intent = new Intent(context, MovieRequestIntentService.class);
        intent.setAction(action);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_REQUEST_PARAMETER, parameter);
        bundle.putParcelable(MOVIE_RECEIVER, receiver);
        intent.putExtras(bundle);
        return intent;
    }

    public MovieRequestIntentService() {
        super("MovieRequestIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            MovieRequestParameter parameter = intent.getParcelableExtra(MOVIE_REQUEST_PARAMETER);
            MovieRequest request = MovieRequestFactory.getRequest(action, parameter);
            ResultReceiver receiver = intent.getParcelableExtra(MOVIE_RECEIVER);
            handleResponse(action, request, receiver);
            this.stopSelf();
        }
    }

    @SuppressWarnings("unchecked")
    private void handleResponse(String action, MovieRequest request, ResultReceiver receiver) {
        Bundle bundle = new Bundle();
        Object items = request != null ? request.execute() : new ArrayList<>();
        bundle.putParcelableArrayList(MOVIE_RESULT, (ArrayList<MovieParcelable>) items);
        receiver.send(MOVIE_RESULT_CODE, bundle);
    }
}
