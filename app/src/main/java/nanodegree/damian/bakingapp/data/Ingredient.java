package nanodegree.damian.bakingapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by robert_damian on 03.06.2018.
 */
@Parcel
@Entity(
    foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipe_id"))
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "recipe_id")
    private int recipeId;
    private double quantity;
    private String measure;
    @SerializedName("ingredient")
    @NonNull
    private String name;

    @Ignore
    public Ingredient() {
        name = "";
    }

    public Ingredient(int id, int recipeId, double quantity, String measure, @NonNull String name) {
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

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

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
