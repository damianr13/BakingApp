package nanodegree.damian.bakingapp.helpers;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import nanodegree.damian.bakingapp.R;

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

}
