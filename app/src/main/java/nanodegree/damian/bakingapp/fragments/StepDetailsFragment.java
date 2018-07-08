package nanodegree.damian.bakingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.RecipeActivity;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.data.RecipeStep;
import nanodegree.damian.bakingapp.helpers.PlayerStateInfo;

import static com.google.android.exoplayer2.ExoPlayer.STATE_ENDED;

/**
 * Created by robert_damian on 07.07.2018.
 */

public class StepDetailsFragment extends Fragment implements ExoPlayer.EventListener{

    public static final String TAG = Fragment.class.getName();

    public static final String EXTRA_RECIPE = RecipeActivity.EXTRA_RECIPE;
    public static final String EXTRA_STEP_INDEX = "Extra step index";
    public static final String EXTRA_PLAYER_STATE_INFO = "Extra player state info";

    @BindView(R.id.pl_step_media)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_step_description)
    TextView mDescriptionTextView;
    @BindView(R.id.iv_thumbnail_media)
    ImageView mThumbnailImageView;
    @BindView(R.id.btn_steps_prev)
    Button mPreviousStepButton;
    @BindView(R.id.btn_steps_next)
    Button mNextStepButton;
    @BindView(R.id.tv_timer)
    TextView mTimerTextView;

    private Recipe mRecipe;
    private int mStepIndex;
    private SimpleExoPlayer mExoPlayer;
    private Timer mContinueTimer;

    private IStepNavigator mStepNavigator;

    private PlayerStateInfo mInitialPlayerStateInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() == null) {
            return rootView;
        }

        mRecipe = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(EXTRA_RECIPE));
        if (mStepIndex == 0) {
            mStepIndex = getActivity().getIntent().getIntExtra(EXTRA_STEP_INDEX, 0);
        }

        mDescriptionTextView.setText(getStep().getDescription());
        setupButtons();

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_PLAYER_STATE_INFO)) {
            mInitialPlayerStateInfo = Parcels.unwrap(
                    savedInstanceState.getParcelable(EXTRA_PLAYER_STATE_INFO));
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mStepNavigator = (IStepNavigator) context;
        } catch (ClassCastException ex) {
            Log.e(TAG, "Context must implement OnRecipeControlsClickListener", ex);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            loadMedia();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            loadMedia();
        }
    }

    public void setRecipe(Recipe recipe) {
        this.mRecipe = recipe;
    }

    public void setStepIndex(int stepIndex) {
        this.mStepIndex = stepIndex;
    }

    public void setStepNavigator(IStepNavigator stepNavigator) {
        this.mStepNavigator = stepNavigator;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        PlayerStateInfo playerStateInfo = new PlayerStateInfo();
        playerStateInfo.isPlaying = mExoPlayer.getPlayWhenReady();
        playerStateInfo.currentPosition = mExoPlayer.getCurrentPosition();

        outState.putParcelable(EXTRA_PLAYER_STATE_INFO, Parcels.wrap(playerStateInfo));
        super.onSaveInstanceState(outState);
    }

    private void setupButtons() {
        mPreviousStepButton.setEnabled(!isFirstStep());
        mNextStepButton.setEnabled(!isLastStep());
    }

    private boolean isLastStep() {
        return mStepIndex == mRecipe.getStepList().size() - 1;
    }

    private boolean isFirstStep() {
        return mStepIndex == 0;
    }

    protected RecipeStep getStep() {
        return mRecipe.getStepList().get(mStepIndex);
    }

    private void loadMedia() {
        initVideoPlayer();
        if (getStep().getVideoURL() != null && !"".equals(getStep().getVideoURL())) {
            mThumbnailImageView.setVisibility(View.GONE);
            setupVideoPlayer(getStep().getVideoURL());
            return ;
        }
        mPlayerView.setVisibility(View.GONE);

        if (getStep().getThumbnailURL() != null && !"".equals(getStep().getThumbnailURL())) {
            Picasso.get().load(getStep().getThumbnailURL()).into(mThumbnailImageView);
            return ;
        }

        mThumbnailImageView.setImageResource(R.mipmap.recipe_default);
    }

    private void initVideoPlayer() {
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector(),
                new DefaultLoadControl());
        mExoPlayer.addListener(this);
        mPlayerView.setPlayer(mExoPlayer);

        if (mInitialPlayerStateInfo != null) {
            mExoPlayer.setPlayWhenReady(mInitialPlayerStateInfo.isPlaying);
            mExoPlayer.seekTo(mInitialPlayerStateInfo.currentPosition);
        }
    }

    private void setupVideoPlayer(String videoURL) {
        Activity parentActivity = getActivity();
        if (parentActivity == null) {
            return ;
        }

        String userAgent = Util.getUserAgent(parentActivity,
                parentActivity.getApplication().getPackageName());
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL),
                new DefaultDataSourceFactory(parentActivity, userAgent),
                new DefaultExtractorsFactory(),null, null);

        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        if (mContinueTimer != null) {
            mContinueTimer.cancel();
            mContinueTimer = null;
        }

        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }

        super.onPause();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == STATE_ENDED) {
            startTimer();
            mTimerTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @OnClick(R.id.btn_steps_next)
    public void clickOnNextStep() {
        mStepNavigator.goToNextStep();
    }

    @OnClick(R.id.btn_steps_prev)
    public void clickOnPrevStep() {
        mStepNavigator.goToPreviousStep();
    }

    @OnClick(R.id.fl_step_holder)
    public void screenTap(View v) {
        if (mContinueTimer == null) {
            return;
        }

        mContinueTimer.cancel();
        mTimerTextView.setVisibility(View.INVISIBLE);
        mContinueTimer = null;
    }

    private void startTimer() {
        // should not start outside an activity
        if (getActivity() == null) {
            return ;
        }

        mContinueTimer = new Timer();
        mContinueTimer.scheduleAtFixedRate(new TimerTask() {
            private int delay = 5;

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTimerTextView.setText(String.valueOf(delay));
                        delay--;
                    }
                });
                if (delay <= 0) {
                    mStepNavigator.goToNextStep();
                }
            }
        }, 0, TimeUnit.SECONDS.toMillis(1));
    }

    public static interface IStepNavigator {
        public void goToNextStep();
        public void goToPreviousStep();
    }
}
