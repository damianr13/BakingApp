package nanodegree.damian.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.parceler.Parcels;

import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.fragments.StepDetailsFragment;
import nanodegree.damian.bakingapp.fragments.StepListFragment;
import nanodegree.damian.bakingapp.helpers.BakingUtils;

/**
 * Created by robert_damian on 03.07.2018.
 */

public class StepListActivity extends AppCompatActivity
        implements StepListFragment.OnStepSelectionListener,
            StepDetailsFragment.IStepNavigator{

    public static final String EXTRA_RECIPE = RecipeActivity.EXTRA_RECIPE;
    public static final String EXTRA_STEP_INDEX = StepActivity.EXTRA_STEP_INDEX;

    private Recipe mRecipe;
    private int mStepIndex;

    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (bundle == null) {
            return ;
        }

        mTwoPane = findViewById(R.id.slave_step_details) != null;
        mRecipe = Parcels.unwrap(bundle.getParcelable(EXTRA_RECIPE));

        if (mTwoPane) {
            mStepIndex = bundle.getInt(EXTRA_STEP_INDEX, 0);
            replaceStepFragment(mStepIndex);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_RECIPE, Parcels.wrap(mRecipe));
        outState.putInt(EXTRA_STEP_INDEX, mStepIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void selectStep(int index) {
        mStepIndex = index;
        if (mTwoPane) {
            replaceStepFragment(index);
            return ;
        }

        launchStepActivity(index);
    }

    private void replaceStepFragment(int index) {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.setRecipe(mRecipe);
        stepDetailsFragment.setStepIndex(index);
        stepDetailsFragment.setStepNavigator(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.slave_step_details, stepDetailsFragment)
                .commit();
    }

    private void launchStepActivity(int index) {
        BakingUtils.launchStepActivity(this, mRecipe, index);
    }

    @Override
    public void goToNextStep() {
        selectStep(mStepIndex + 1);
    }

    @Override
    public void goToPreviousStep() {
        selectStep(mStepIndex - 1);
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
