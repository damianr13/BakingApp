package nanodegree.damian.bakingapp.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nanodegree.damian.bakingapp.data.Ingredient;

/**
 * Created by robert_damian on 06.06.2018.
 */
@Dao
public interface IngredientDao {
    @Query("SELECT * FROM Ingredient WHERE recipe_id = :recipeId")
    List<Ingredient> getIngredientsByRecipe(int recipeId);

    @Insert
    void insertIngredient(Ingredient ingredient);

    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Query("DELETE FROM Ingredient")
    void deleteAll();

    @Insert
    void insertAll(Ingredient... ingredients);
}
