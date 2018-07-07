package nanodegree.damian.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
import nanodegree.damian.bakingapp.fragments.StepDetailsFragment;
import nanodegree.damian.bakingapp.helpers.BakingUtils;

import static com.google.android.exoplayer2.ExoPlayer.STATE_ENDED;

public class StepActivity extends AppCompatActivity implements StepDetailsFragment.IStepNavigator{

    public static String EXTRA_RECIPE = StepDetailsFragment.EXTRA_RECIPE;
    public static String EXTRA_STEP_INDEX = StepDetailsFragment.EXTRA_STEP_INDEX;

    private int mStepIndex;
    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent startingIntent = getIntent();
        if (startingIntent == null) {
            return ;
        }

        mRecipe = Parcels.unwrap(startingIntent.getParcelableExtra(EXTRA_RECIPE));
        mStepIndex = startingIntent.getIntExtra(EXTRA_STEP_INDEX, 0);
    }

    @Override
    public void goToNextStep() {
        if (BakingUtils.isLastStep(mStepIndex, mRecipe)) {
            return ;
        }

        launchStep(mStepIndex + 1);
    }

    @Override
    public void goToPreviousStep() {
        if (BakingUtils.isFirstStep(mStepIndex, mRecipe)) {
            return ;
        }

        launchStep(mStepIndex - 1);
    }

    private void launchStep(int stepIndex) {
        BakingUtils.launchStepActivity(this, mRecipe, stepIndex);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = new Intent(this, RecipeActivity.class);
                upIntent.putExtra(RecipeActivity.EXTRA_RECIPE, Parcels.wrap(mRecipe));

                NavUtils.navigateUpTo(this, upIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
