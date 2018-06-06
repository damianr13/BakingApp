package nanodegree.damian.bakingapp.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nanodegree.damian.bakingapp.data.RecipeStep;
import retrofit2.http.DELETE;

/**
 * Created by robert_damian on 06.06.2018.
 */
@Dao
public interface RecipeStepDao {
    @Query("SELECT * FROM step WHERE recipe_id = :recipeId")
    List<RecipeStep> getStepsForRecipe(int recipeId);

    @Insert
    void insertRecipeStep(RecipeStep recipeStep);

    @Delete
    void deleteRecipeStep(RecipeStep recipeStep);

    @Query("DELETE FROM step")
    void deleteAll();

    @Insert
    void insertAll(RecipeStep... recipeSteps);
}
