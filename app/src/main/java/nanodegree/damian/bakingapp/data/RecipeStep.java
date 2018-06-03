package nanodegree.damian.bakingapp.data;

import com.google.gson.Gson;

/**
 * Created by robert_damian on 03.06.2018.
 */

public class RecipeStep {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public static RecipeStep fromJSON(String json) {
        return new Gson().fromJson(json, RecipeStep.class);
    }

    public int getId() {
        return id;
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
}
