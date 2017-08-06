package com.example.android.popular.movie.view.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.model.api.MovieApi;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.entity.Review;
import com.example.android.popular.movie.model.entity.Video;
import com.example.android.popular.movie.presenter.moviedetail.MovieDetailPresenter;
import com.example.android.popular.movie.presenter.moviedetail.MovieDetailPresenterImpl;
import com.example.android.popular.movie.presenter.moviedetail.MovieDetailView;
import com.example.android.popular.movie.utils.Constants;
import com.example.android.popular.movie.utils.MovieUtils;
import com.example.android.popular.movie.view.adapter.ReviewAdapter;
import com.example.android.popular.movie.view.adapter.VideoAdapter;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements MovieDetailView, View.OnClickListener,
        VideoAdapter.VideoAdapterOnClickHandler, ReviewAdapter.ReviewAdapterOnClickHandler {

    private ImageView image;
    private TextView description;
    private TextView releaseDate;
    private TextView userRating;
    private TextView title;
    private ImageView startFavoriteIcon;
    private RecyclerView videoRecyclerView;
    private RecyclerView reviewRecyclerView;
    private VideoAdapter videoAdapter;
    private ReviewAdapter reviewAdapter;
    private ProgressDialog progress;
    private MovieDetailPresenter presenter;
    private List<Video> videos;
    private List<Review> reviews;
    private Movie movie;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        presenter = new MovieDetailPresenterImpl(this);
        initUI(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreSate(savedInstanceState);
    }

    private void initUI(View view) {
        title = (TextView) view.findViewById(R.id.title_view);
        image = (ImageView) view.findViewById(R.id.image_view_poster);
        description = (TextView) view.findViewById(R.id.text_view_overview);
        releaseDate = (TextView) view.findViewById(R.id.text_view_release_date);
        userRating = (TextView) view.findViewById(R.id.text_view_vote_average);
        startFavoriteIcon = (ImageView) view.findViewById(R.id.action_favorite_start);
        startFavoriteIcon.setOnClickListener(this);

        progress = new ProgressDialog(getContext());
        progress.setMessage(getContext().getString(R.string.loading_movie_details));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        videoRecyclerView = (RecyclerView) view.findViewById(R.id.video_recycler_view);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoRecyclerView.setHasFixedSize(true);
        videoAdapter = new VideoAdapter(this);
        videoRecyclerView.setAdapter(videoAdapter);

        reviewRecyclerView = (RecyclerView) view.findViewById(R.id.review_recycler_view);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewRecyclerView.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(this);
        reviewRecyclerView.setAdapter(reviewAdapter);

        if (getActivity().getIntent() != null
                && getActivity().getIntent().getExtras().containsKey(Constants.MOVIE_EXTRA)) {
            Bundle bundle = getActivity().getIntent().getExtras();
            Movie movie = bundle.getParcelable(Constants.MOVIE_EXTRA);
            assert movie != null;
            loadMovieDetails(movie);
        }

    }

    private void loadMovieDetails(Movie movie) {
        this.movie = movie;
        title.setText(movie.getOriginalTitle());
        title.setSelected(true);
        description.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        userRating.setText(String.valueOf(movie.getVoteAverage()));
        Picasso.with(getContext()).load(MovieApi.getImageUri(movie.getBackdropPath()))
                .error(android.R.drawable.stat_notify_error).placeholder(android.R.drawable.stat_sys_download)
                .into(image);
        presenter.updatFavoriteState(movie.getId());
    }

    @Override
    public void onClick(View v) {
        presenter.handleFavoriteMovies(movie);
    }

    @Override
    public void onClick(Video video) {
        MovieUtils.playVideo(getContext(), video.getKey());
    }

    @Override
    public void onClick(Review review) {

    }

    @Override
    public void onShowLoader(final boolean show) {
        if (show) {
            progress.show();
        } else {
            if (progress.isShowing())
                progress.hide();
        }
    }

    @Override
    public Context getContextReference() {
        return getContext();
    }

    @Override
    public Activity getActivityReference() {
        return getActivity();
    }

    @Override
    public void ShowErrorMessage(String message) {
        if (getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
    }

    @Override
    public void onVideosLoaded(List<Video> videos) {
        showVideos(videos);
    }

    @Override
    public void onReviewsLoaded(List<Review> reviews) {
        showReviews(reviews);
    }

    @Override
    public void updateFavoriteIcon(final boolean movieAdded) {
        this.movie.setPrefered(movieAdded);
        startFavoriteIcon.post(new Runnable() {
            @Override
            public void run() {
                startFavoriteIcon.setImageDrawable(ContextCompat.getDrawable(getContext(),
                        movieAdded ? android.R.drawable.star_big_on : android.R.drawable.star_big_off));
            }
        });
    }

    private void showVideos(final List<Video> videos) {
        this.videos = videos;
        videoRecyclerView.post(() -> videoAdapter.setVideos(videos));
    }

    private void showReviews(final List<Review> reviews) {
        this.reviews = reviews;
        reviewRecyclerView.post(() -> reviewAdapter.setReviews(reviews));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelableArrayList(Constants.REVIEWS_KEY, (ArrayList<? extends Parcelable>) reviews);
            outState.putParcelableArrayList(Constants.VIDEOS_KEY, (ArrayList<? extends Parcelable>) videos);
            outState.putParcelable(Constants.MOVIES_DETAILS_KEY, movie);
        }
        super.onSaveInstanceState(outState);
    }

    private void restoreSate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable(Constants.MOVIES_DETAILS_KEY);
            videos = savedInstanceState.getParcelableArrayList(Constants.VIDEOS_KEY);
            reviews = savedInstanceState.getParcelableArrayList(Constants.REVIEWS_KEY);
            loadMovieDetails(movie);
            showVideos(videos);
            showReviews(reviews);
        } else {
            presenter.loadDetails(movie.getId());
        }
    }
}
