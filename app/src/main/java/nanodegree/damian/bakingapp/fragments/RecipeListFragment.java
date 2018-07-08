package nanodegree.damian.bakingapp.fragments;

import android.content.Context;
import android.content.CursorLoader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.helpers.LoaderHelper;
import nanodegree.damian.bakingapp.helpers.NetworkHelper;
import nanodegree.damian.bakingapp.helpers.RecipesLoader;
import nanodegree.damian.bakingapp.visuals.RecipeAdapter;

/**
 * Fragment displaying the list of all the recipes
 */
public class RecipeListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<Recipe>>, LoaderHelper.ResultsDisplayer<List<Recipe>>{

    public static final String TAG = RecipeListFragment.class.getName();
    private static final int RECIPES_LOADER = 13;

    @BindView(R.id.rv_recipes_view)
    RecyclerView mRecyclerView;

    private RecipeAdapter mAdapter;
    private LoaderHelper<List<Recipe>> mLoaderHelper;
    private RecipeListFragmentOwnerCallbacks mRecipeListFragmentOwnerCallbacks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container,
                false);
        ButterKnife.bind(this, rootView);

        mAdapter = new RecipeAdapter(null, mRecipeListFragmentOwnerCallbacks);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mLoaderHelper = new LoaderHelper.LoaderHelperBuilder()
                .setHelperId(RECIPES_LOADER)
                .setDisplayedResultsView(mRecyclerView)
                .setErrorView(rootView.findViewById(R.id.tv_error_view))
                .setLoadingView(rootView.findViewById(R.id.pb_progress_view))
                .setResultDisplayer(this)
                .build(getContext());

        if (NetworkHelper.isConnected(getContext())) {
            if (getActivity() == null) {
                return rootView;
            }
            mLoaderHelper.loadStarted();
            getActivity().getSupportLoaderManager().initLoader(RECIPES_LOADER,
                    null, this);

        }
        else {
            mLoaderHelper.loadFailed();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mRecipeListFragmentOwnerCallbacks = (RecipeListFragmentOwnerCallbacks) context;
        } catch (ClassCastException ex) {
            Log.e(TAG, "Context must implement RecipeListFragmentOwnerCallbacks", ex);
        }
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case RECIPES_LOADER:
                return new RecipesLoader(getContext());
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
        mRecipeListFragmentOwnerCallbacks.loadingFinished(result);
    }

    public interface RecipeListFragmentOwnerCallbacks {
        void recipeSelected(Recipe recipe);
        void loadingFinished(List<Recipe> recipeList);
    }
}
