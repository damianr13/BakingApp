package nanodegree.damian.bakingapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by robert_damian on 03.06.2018.
 */
@Parcel
@Entity(tableName = "step",
    foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipe_id"))
public class RecipeStep {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primary_key")
    private int primaryKey;
    @ColumnInfo(name = "recipe_id")
    private int recipeId;
    @ColumnInfo(name = "step_id")
    @SerializedName("id")
    private int stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    @Ignore
    public RecipeStep() {

    }

    public RecipeStep(int primaryKey, int recipeId, int stepId, String shortDescription,
                      String description, String videoURL, String thumbnailURL) {
        this.primaryKey = primaryKey;
        this.recipeId = recipeId;
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public static RecipeStep fromJSON(String json) {
        return new Gson().fromJson(json, RecipeStep.class);
    }

    public int getStepId() {
        return stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }
}
