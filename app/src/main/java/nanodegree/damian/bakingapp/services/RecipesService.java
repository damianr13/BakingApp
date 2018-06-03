package nanodegree.damian.bakingapp.services;

import java.util.List;

import nanodegree.damian.bakingapp.data.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by robert_damian on 03.06.2018.
 */

public interface RecipesService {

    @GET("baking.json")
    public Call<List<Recipe>> recipesList();
}
