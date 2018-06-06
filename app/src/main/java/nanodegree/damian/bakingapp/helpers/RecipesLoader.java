package nanodegree.damian.bakingapp.helpers;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

import nanodegree.damian.bakingapp.data.Ingredient;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.data.RecipeStep;
import nanodegree.damian.bakingapp.data.database.AppDatabase;
import nanodegree.damian.bakingapp.services.RecipesRequestService;

/**
 * Created by robert_damian on 03.06.2018.
 */

public class RecipesLoader extends AsyncTaskLoader<List<Recipe>>{
    private List<Recipe> mResult;

    public RecipesLoader(Context context) {
        super(context);
        mResult = null;
    }

    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
            return ;
        }
        forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {
        try {
            List<Recipe> recipeListFromServer =
                    RecipesRequestService.getInstance().loadRecipeList();

            // store data
            AppDatabase.getInstance(getContext()).ingredientDao().deleteAll();
            AppDatabase.getInstance(getContext()).recipeStepDao().deleteAll();
            AppDatabase.getInstance(getContext()).recipeDao().deleteAll();

            AppDatabase.getInstance(getContext()).recipeDao()
                    .insertAll(recipeListFromServer.toArray(new Recipe[recipeListFromServer.size()]));
            for (Recipe recipe : recipeListFromServer) {
                insertIngredients(recipe);
                insertSteps(recipe);
            }

            return recipeListFromServer;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void insertIngredients(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredientList();
        for (Ingredient ingredient : ingredients) {
            ingredient.setRecipeId(recipe.getId());
        }

        AppDatabase.getInstance(getContext()).ingredientDao().insertAll(
                ingredients.toArray(new Ingredient[ingredients.size()]));
    }

    private void insertSteps(Recipe recipe) {
        List<RecipeStep> recipeSteps = recipe.getStepList();
        for (RecipeStep recipeStep : recipeSteps) {
            recipeStep.setRecipeId(recipe.getId());
        }
        
        AppDatabase.getInstance(getContext()).recipeStepDao().insertAll(
                recipeSteps.toArray(new RecipeStep[recipeSteps.size()]));
    }

    @Override
    public void deliverResult(List<Recipe> data) {
        mResult = data;
        super.deliverResult(data);
    }
}
