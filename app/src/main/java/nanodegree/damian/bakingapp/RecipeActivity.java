package nanodegree.damian.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nanodegree.damian.bakingapp.data.Ingredient;
import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.fragments.RecipeDetailsFragment;
import nanodegree.damian.bakingapp.helpers.BakingUtils;
import nanodegree.damian.bakingapp.visuals.IngredientArrayAdapter;

public class RecipeActivity extends AppCompatActivity implements
        RecipeDetailsFragment.OnRecipeStepsClickListener{

    public static final String EXTRA_RECIPE = "RECIPE";

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_RECIPE));
    }

    @Override
    public void seeRecipeSteps() {
        BakingUtils.launchFirstStepActivity(this, mRecipe);
    }
}
