package com.example.android.popular.movie.presenter.moviedetail;

import static com.example.android.popular.movie.model.api.request.MovieRequestFactory.REVIEWS_LIST_ACTION;
import static com.example.android.popular.movie.model.api.request.MovieRequestFactory.VIDEOS_LIST_ACTION;

import java.util.ArrayList;
import java.util.List;

import com.example.android.popular.movie.model.api.request.MovieRequestParameter;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.entity.MovieParcelable;
import com.example.android.popular.movie.model.entity.Review;
import com.example.android.popular.movie.model.entity.Video;
import com.example.android.popular.movie.presenter.MovieInteractor;
import com.example.android.popular.movie.presenter.MovieInteractorImpl;
import com.example.android.popular.movie.utils.Constanst;
import com.example.android.popular.movie.utils.PreferenceUtils;

/**
 * Created by fabianantoniohoyospulido on 7/29/17.
 */

public class MovieDetailPresenterImpl implements MovieDetailPresenter, MovieInteractor.OnResponseListener {
    private MovieDetailView view;
    private MovieInteractor interactor;

    public MovieDetailPresenterImpl(MovieDetailView view) {
        this.view = view;
        this.interactor = new MovieInteractorImpl();
    }

    @Override
    public void loadDetails(int movieId) {
        view.onShowLoader(true);
        MovieRequestParameter.Builder builder = new MovieRequestParameter.Builder();
        builder.movieId(movieId);
        interactor.processRequest(REVIEWS_LIST_ACTION, builder.build(), this);
        interactor.processRequest(VIDEOS_LIST_ACTION, builder.build(), this);

    }

    @Override
    public void handleFavoriteMovies(Movie movie) {
        interactor.addOrRemoveMovieToFavorites(movie, this);
    }

    @Override
    public int getCurrentOption() {
        return PreferenceUtils.getInstance().getIntValue(Constanst.MENU_ITEM_ID);
    }

    @Override
    public void updatFavoriteState(long movieId) {
        interactor.findFavoriteMovie(movieId, this);
    }

    @Override
    public void onResponseError() {
        view.onShowLoader(false);
    }

    @Override
    public void onResponseSuccess(List<MovieParcelable> movieParcelables) {
        view.onShowLoader(false);
        if (isMovieList(movieParcelables.get(0))) {
            fillVideos(movieParcelables);
        } else {
            fillReviews(movieParcelables);
        }
    }

    @Override
    public void onMovieAdded() {
        view.updateFavoriteIcon(true);
    }

    @Override
    public void onMovieRemoved() {
        view.updateFavoriteIcon(false);
    }

    @Override
    public void onErrorMovieFavoriteList() {
        view.ShowErrorMessage(Constanst.ERROR_MESSAGE_MOVIE_FAVORITE);
    }

    @Override
    public void onFavoriteMovies(List<Movie> movies) {

    }

    @Override
    public void updateFavoriteStateUI(boolean added) {
        view.updateFavoriteIcon(added);
    }

    private void fillVideos(List<MovieParcelable> parcelableList) {
        List<Video> videos = new ArrayList<>();
        for (MovieParcelable parcelable : parcelableList) {
            videos.add(parcelable.getVideo());
        }
        view.onVideosLoaded(videos);
    }

    private void fillReviews(List<MovieParcelable> parcelableList) {
        List<Review> reviews = new ArrayList<>();
        for (MovieParcelable parcelable : parcelableList) {
            reviews.add(parcelable.getReview());
        }
        view.onReviewsLoaded(reviews);
    }

    private boolean isMovieList(MovieParcelable firstParcelable) {
        return firstParcelable.getVideo() != null;
    }

}
