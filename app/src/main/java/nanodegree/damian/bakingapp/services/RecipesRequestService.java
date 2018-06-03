package nanodegree.damian.bakingapp.services;

import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import java.io.IOException;
import java.util.List;

import nanodegree.damian.bakingapp.data.Recipe;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by robert_damian on 03.06.2018.
 */
public class RecipesRequestService {

    public static final String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static RecipesRequestService sInstance;

    private RecipesService mService;

    private RecipesRequestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(RecipesService.class);
    }

    public static synchronized RecipesRequestService getInstance() {
        if (sInstance == null) {
            sInstance = new RecipesRequestService();
        }

        return sInstance;
    }

    @WorkerThread
    public List<Recipe> loadRecipeList() throws IOException {
        return mService.recipesList().execute().body();
    }
}
