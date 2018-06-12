package nanodegree.damian.bakingapp.helpers;

import android.content.Context;

import java.util.List;

import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.data.database.AppDatabase;

/**
 * Created by robert_damian on 12.06.2018.
 */

public class DatabaseRecipesLoader extends RecipesLoader{

    public DatabaseRecipesLoader(Context context) {
        super(context);
    }

    @Override
    public List<Recipe> loadInBackground() {
        return AppDatabase.getInstance(getContext()).recipeDao().loadAllRecipes();
    }
}
