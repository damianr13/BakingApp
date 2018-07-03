package nanodegree.damian.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.data.RecipeStep;
import nanodegree.damian.bakingapp.helpers.BakingUtils;

import static com.google.android.exoplayer2.ExoPlayer.STATE_ENDED;

public class StepActivity extends AppCompatActivity implements ExoPlayer.EventListener{

    public static final String EXTRA_RECIPE = RecipeActivity.EXTRA_RECIPE;
    public static final String EXTRA_STEP_INDEX = "Extra step index";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_RECIPE));
        mStepIndex = getIntent().getIntExtra(EXTRA_STEP_INDEX, 0);

        mDescriptionTextView.setText(getStep().getDescription());
        setupButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMedia();
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
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(),
                new DefaultLoadControl());
        mExoPlayer.addListener(this);
        mPlayerView.setPlayer(mExoPlayer);
    }

    private void setupVideoPlayer(String videoURL) {
        String userAgent = Util.getUserAgent(this, getApplication().getPackageName());
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL),
                new DefaultDataSourceFactory(this, userAgent),
                new DefaultExtractorsFactory(),null, null);

        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @OnClick(R.id.btn_steps_prev)
    public void seePreviousStep(View v) {
        if (isFirstStep()) {
            return ;
        }

        launchStep(mStepIndex - 1);
    }

    @OnClick(R.id.btn_steps_next)
    public void seeNextStep(View v) {
        if (isLastStep()) {
            return ;
        }

        launchStep(mStepIndex + 1);
    }

    @OnClick(R.id.rl_step_holder)
    public void clickOnScreen(View v) {
        if (mContinueTimer == null) {
            return;
        }
        mContinueTimer.cancel();
        mTimerTextView.setVisibility(View.INVISIBLE);
        mContinueTimer = null;
    }

    private void launchStep(int stepIndex) {
        BakingUtils.launchStepActivity(this, mRecipe, stepIndex);
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

    @Override
    protected void onPause() {
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

    private void startTimer() {
        mContinueTimer = new Timer();
        mContinueTimer.scheduleAtFixedRate(new TimerTask() {
            private int delay = 5;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTimerTextView.setText(String.valueOf(delay));
                        delay--;
                    }
                });
                if (delay <= 0) {
                    seeNextStep(mNextStepButton);
                }
            }
        }, 0, TimeUnit.SECONDS.toMillis(1));
    }
}
