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
        RecipeDetailsFragment.OnRecipeStepsClickListener {

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.lv_ingredients) != null) {
            mTwoPane = true;
        }

        setFlagOnIdlingResource(false);
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
    public void seeRecipeSteps() {
        BakingUtils.launchFirstStepActivity(this, mRecipe);
    }
}
