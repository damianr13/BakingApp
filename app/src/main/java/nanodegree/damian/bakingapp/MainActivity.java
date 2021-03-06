package nanodegree.damian.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import java.util.List;

import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.fragments.RecipeDetailsFragment;
import nanodegree.damian.bakingapp.fragments.RecipeListFragment;
import nanodegree.damian.bakingapp.helpers.BakingUtils;
import nanodegree.damian.bakingapp.helpers.test.SimpleIdlingResource;

public class MainActivity extends AppCompatActivity implements
        RecipeListFragment.RecipeListFragmentOwnerCallbacks,
        RecipeDetailsFragment.OnRecipeControlsClickListener {

    public static final String EXTRA_RECIPE = RecipeActivity.EXTRA_RECIPE;

    private boolean mTwoPane;
    private Recipe mRecipe;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipe == null) {
            return ;
        }

        outState.putParcelable(EXTRA_RECIPE, Parcels.wrap(mRecipe));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.slave_list_fragment) != null) {
            mTwoPane = true;
        }

        setFlagOnIdlingResource(false);

        if (savedInstanceState == null || !mTwoPane) {
            return ;
        }

        mRecipe = Parcels.unwrap(savedInstanceState.getParcelable(EXTRA_RECIPE));
        swapRecipeDetailsFragment();
    }

    private void setFlagOnIdlingResource(boolean flag) {
        if (mIdlingResource == null) {
            return;
        }

        mIdlingResource.setIdleState(flag);
    }

    @Override
    public void recipeSelected(Recipe recipe) {
        if (mTwoPane) {
            mRecipe = recipe;
            swapRecipeDetailsFragment();
        } else {
            launchRecipeDetailsActivity(recipe);
        }
    }

    @Override
    public void loadingFinished(List<Recipe> recipeList) {
        setFlagOnIdlingResource(true);

        if (mRecipe != null) {
            // if you already have a recipe we don't care
            return ;
        }

        if (!mTwoPane) {
            return ;
        }

        if (recipeList == null || recipeList.size() == 0) {
            return ;
        }

        // display first recipe by default
        mRecipe = recipeList.get(0);
        swapRecipeDetailsFragment();
    }

    public void launchRecipeDetailsActivity(Recipe recipe) {
        Intent recipeActivityIntent = new Intent(this, RecipeActivity.class);
        recipeActivityIntent.putExtra(RecipeActivity.EXTRA_RECIPE, Parcels.wrap(recipe));
        startActivity(recipeActivityIntent);
    }

    public void swapRecipeDetailsFragment() {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setRecipe(mRecipe);
        recipeDetailsFragment.setRecipeStepsClickListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.slave_list_fragment, recipeDetailsFragment)
                .commit();
    }

    @Override
    public void startRecipeFromBeginning() {
        // if we have this action here it means it's a tablet, so we call the 2 pane version of the
        // StepListActivity
        showStepList();
    }

    @Override
    public void showStepList() {
        BakingUtils.launchStepListActivity(this, mRecipe);
    }
}
