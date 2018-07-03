package nanodegree.damian.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.fragments.StepListFragment;
import nanodegree.damian.bakingapp.helpers.BakingUtils;

/**
 * Created by robert_damian on 03.07.2018.
 */

public class StepListActivity extends AppCompatActivity
        implements StepListFragment.OnStepSelectionListener{

    public static final String EXTRA_RECIPE = RecipeActivity.EXTRA_RECIPE;

    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_RECIPE));
        if (savedInstanceState == null) {
            return ;
        }

        mRecipe = Parcels.unwrap(savedInstanceState.getParcelable(EXTRA_RECIPE));
    }

    @Override
    public void selectStep(int index) {
        BakingUtils.launchStepActivity(this, mRecipe, index);
    }
}
