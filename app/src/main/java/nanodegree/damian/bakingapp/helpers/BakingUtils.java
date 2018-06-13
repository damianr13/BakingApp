package nanodegree.damian.bakingapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import nanodegree.damian.bakingapp.R;
import nanodegree.damian.bakingapp.StepActivity;
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
        Intent stepsIntent = new Intent(context, StepActivity.class);
        stepsIntent.putExtra(StepActivity.EXTRA_RECIPE, Parcels.wrap(recipe));
        stepsIntent.putExtra(StepActivity.EXTRA_STEP_INDEX, 0);

        context.startActivity(stepsIntent);
    }
}
