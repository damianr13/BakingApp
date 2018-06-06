package nanodegree.damian.bakingapp.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nanodegree.damian.bakingapp.data.Recipe;

/**
 * Created by robert_damian on 06.06.2018.
 */

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe")
    List<Recipe> loadAllRecipes();

    @Insert
    void insertRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("DELETE FROM recipe")
    void deleteAll();

    @Insert
    void insertAll(Recipe... recipes);
}
