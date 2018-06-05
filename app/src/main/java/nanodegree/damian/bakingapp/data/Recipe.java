package nanodegree.damian.bakingapp.data;

import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.parceler.Parcel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert_damian on 03.06.2018.
 */
@Parcel
public class Recipe{
    private int id;
    private String name;
    private int servings;
    private String image;
    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;
    @SerializedName("steps")
    private List<RecipeStep> stepList;

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public static Recipe fromJSON(String json) {
        return new Gson().fromJson(json, Recipe.class);
    }

    public static List<Recipe> loadListFromJSON(String json) {
        Type listType = new TypeToken<ArrayList<Recipe>>(){}.getType();

        return new Gson().fromJson(json, listType);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<RecipeStep> getStepList() {
        return stepList;
    }
}
