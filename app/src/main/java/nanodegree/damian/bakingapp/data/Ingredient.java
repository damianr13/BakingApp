package nanodegree.damian.bakingapp.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by robert_damian on 03.06.2018.
 */
@Parcel
public class Ingredient {

    private double quantity;
    private String measure;
    @SerializedName("ingredient")
    private String name;

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public static Ingredient fromJSON(String json) {
        return new Gson().fromJson(json, Ingredient.class);
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }
}
