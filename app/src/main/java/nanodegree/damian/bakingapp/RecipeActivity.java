package nanodegree.damian.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.damian.bakingapp.data.Ingredient;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.helpers.BakingUtils;
import nanodegree.damian.bakingapp.visuals.IngredientArrayAdapter;

public class RecipeActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "RECIPE";

    @BindView(R.id.lv_ingredients)
    ListView mIngredientListView;
    @BindView(R.id.iv_recipe_image)
    ImageView mRecipeImageView;

    private Recipe mRecipe;
    private ArrayAdapter<Ingredient> mIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_RECIPE));

        BakingUtils.loadRecipeImageIntoView(mRecipe.getImage(), mRecipeImageView);

        mIngredientsAdapter = new IngredientArrayAdapter(this, mRecipe.getIngredientList());
        mIngredientListView.setAdapter(mIngredientsAdapter);
    }

}
