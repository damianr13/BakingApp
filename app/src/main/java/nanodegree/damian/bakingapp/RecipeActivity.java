package nanodegree.damian.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.fragments.RecipeDetailsFragment;
import nanodegree.damian.bakingapp.helpers.BakingUtils;

public class RecipeActivity extends AppCompatActivity implements
        RecipeDetailsFragment.OnRecipeControlsClickListener {

    public static final String EXTRA_RECIPE = "RECIPE";

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_RECIPE));
    }

    @Override
    public void startRecipeFromBeginning() {
        BakingUtils.launchFirstStepActivity(this, mRecipe);
    }

    @Override
    public void showStepList() {
        BakingUtils.launchStepListActivity(this, mRecipe);
    }
}
