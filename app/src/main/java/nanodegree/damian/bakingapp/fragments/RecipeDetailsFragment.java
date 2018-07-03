package nanodegree.damian.bakingapp.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.RecipeActivity;
import nanodegree.damian.bakingapp.data.Ingredient;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.helpers.BakingUtils;
import nanodegree.damian.bakingapp.visuals.IngredientArrayAdapter;

/**
 * Created by robert_damian on 13.06.2018.
 */

public class RecipeDetailsFragment extends Fragment {

    public static final String TAG = RecipeDetailsFragment.class.getName();
    public static final String EXTRA_RECIPE = RecipeActivity.EXTRA_RECIPE;

    @BindView(R.id.lv_ingredients)
    ListView mIngredientListView;
    @BindView(R.id.iv_recipe_image)
    ImageView mRecipeImageView;

    private OnRecipeControlsClickListener mRecipeControlsClickListener;
    private Recipe mRecipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_details,
                container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() == null) {
            // if there is no parent activity we just return an empty view
            return rootView;
        }

        if (mRecipe == null) {
            if (getActivity().getIntent() == null || !getActivity().getIntent().hasExtra(EXTRA_RECIPE)) {
                // if there is no way to get a recipe we return an empty view early
                return rootView;
            }
            mRecipe = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(EXTRA_RECIPE));
        }

        BakingUtils.loadRecipeImageIntoView(mRecipe.getImage(), mRecipeImageView);

        ArrayAdapter<Ingredient> ingredientArrayAdapter = new IngredientArrayAdapter(
                getActivity(), mRecipe.getIngredientList());
        mIngredientListView.setAdapter(ingredientArrayAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mRecipeControlsClickListener = (OnRecipeControlsClickListener) context;
        } catch (ClassCastException ex) {
            Log.e(TAG, "Context must implement OnRecipeControlsClickListener", ex);
        }
    }

    @OnClick(R.id.btn_start_cooking)
    public void startCooking(View v) {
        mRecipeControlsClickListener.startRecipeFromBeginning();
    }

    @OnClick(R.id.btn_see_steps)
    public void seeStepList(View v) {
        mRecipeControlsClickListener.showStepList();
    }
    
    public void setRecipe(Recipe recipe) {
        this.mRecipe = recipe;
    }

    public void setRecipeStepsClickListener(OnRecipeControlsClickListener recipeControlsClickListener) {
        this.mRecipeControlsClickListener = recipeControlsClickListener;
    }

    public interface OnRecipeControlsClickListener {
        void startRecipeFromBeginning();
        void showStepList();
    }
}
