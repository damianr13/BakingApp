package nanodegree.damian.bakingapp;

import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.helpers.LoaderHelper;
import nanodegree.damian.bakingapp.helpers.NetworkHelper;
import nanodegree.damian.bakingapp.helpers.RecipesLoader;
import nanodegree.damian.bakingapp.visuals.RecipeAdapter;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Recipe>>,
        LoaderHelper.ResultsDisplayer<List<Recipe>>{

    private static final int RECIPES_LOADER = 13;

    @BindView(R.id.rv_recipes_view)
    RecyclerView mRecyclerView;

    private RecipeAdapter mAdapter;
    private LoaderHelper<List<Recipe>> mLoaderHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new RecipeAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLoaderHelper = new LoaderHelper.LoaderHelperBuilder()
                .setHelperId(RECIPES_LOADER)
                .setDisplayedResultsView(mRecyclerView)
                .setErrorView(findViewById(R.id.tv_error_view))
                .setLoadingView(findViewById(R.id.pb_progress_view))
                .setResultDisplayer(this)
                .build(this);

        if (NetworkHelper.isConnected(this)) {
            mLoaderHelper.loadStarted();
            getSupportLoaderManager().initLoader(RECIPES_LOADER, null, this);
        }
        else {
            mLoaderHelper.loadFailed();
        }
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case RECIPES_LOADER:
                return new RecipesLoader(this);
        }

        throw new UnsupportedOperationException("Unimplemented loader: " + i);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> recipes) {
        mLoaderHelper.loadSucceeded(recipes);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mAdapter.swapRecipeList(null);
    }

    @Override
    public void displayResults(int helperId, List<Recipe> result) {
        mAdapter.swapRecipeList(result);
    }
}
