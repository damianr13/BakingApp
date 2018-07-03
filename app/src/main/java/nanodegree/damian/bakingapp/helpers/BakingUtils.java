package nanodegree.damian.bakingapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.StepActivity;
import nanodegree.damian.bakingapp.StepListActivity;
import nanodegree.damian.bakingapp.data.Recipe;

/**
 * Created by robert_damian on 03.06.2018.
 */

public class BakingUtils {

    public static void loadRecipeImageIntoView(String imageURL, ImageView view) {
        if (imageURL == null || imageURL.isEmpty()) {
            view.setImageResource(R.mipmap.recipe_default);
        }
        else {
            Picasso.get().load(imageURL).error(R.mipmap.recipe_default).into(view);
        }
    }

    public static void launchFirstStepActivity(Context context, Recipe recipe) {
        launchStepActivity(context, recipe, 0);
    }

    public static void launchStepListActivity(Context context, Recipe recipe) {
        Intent stepListIntent = new Intent(context, StepListActivity.class);
        stepListIntent.putExtra(StepListActivity.EXTRA_RECIPE, Parcels.wrap(recipe));

        context.startActivity(stepListIntent);
    }

    public static void launchStepActivity(Context context, Recipe recipe, int stepIndex) {
        Intent stepIntent = new Intent(context, StepActivity.class);
        stepIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        stepIntent.putExtra(StepActivity.EXTRA_RECIPE, Parcels.wrap(recipe));
        stepIntent.putExtra(StepActivity.EXTRA_STEP_INDEX, stepIndex);

        context.startActivity(stepIntent);
    }
}
