package nanodegree.damian.bakingapp.helpers;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

import nanodegree.damian.bakingapp.data.Recipe;
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
            return RecipesRequestService.getInstance().loadRecipeList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deliverResult(List<Recipe> data) {
        mResult = data;
        super.deliverResult(data);
    }
}
