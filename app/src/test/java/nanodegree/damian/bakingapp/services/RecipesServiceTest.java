package nanodegree.damian.bakingapp.services;

import com.google.gson.Gson;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import nanodegree.damian.bakingapp.data.Recipe;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by robert_damian on 03.06.2018.
 */

public class RecipesServiceTest {

    @Test
    public void test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipesService service = retrofit.create(RecipesService.class);

        Call<List<Recipe>> recipeListCall = service.recipesList();
        Response<List<Recipe>> recipeListResponse = null;
        try {
            recipeListResponse = recipeListCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }
        if (recipeListResponse == null) {
            assert false;
        }

        Assert.assertEquals(4, recipeListResponse.body().size());
    }
}
