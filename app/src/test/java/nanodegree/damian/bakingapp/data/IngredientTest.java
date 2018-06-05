package nanodegree.damian.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.junit.Assert;
import org.junit.Test;
import org.parceler.Parcels;

/**
 * Created by robert_damian on 03.06.2018.
 */

public class IngredientTest {

    @Test
    public void testFromJsonConversion() {
        String json = "{\n" +
                "  \"ingredient\": \"flour\",\n" +
                "  \"quantity\": 1.2,\n" +
                "  \"measure\": \"kg\"\n" +
                "}";

        Ingredient ingredientFromJSON = Ingredient.fromJSON(json);

        Assert.assertEquals("flour", ingredientFromJSON.getName());
        Assert.assertEquals(1.2, ingredientFromJSON.getQuantity(), .01);
        Assert.assertEquals("kg", ingredientFromJSON.getMeasure());
    }

    @Test
    public void testIngredientParcel() {
        String json = "{\n" +
                "  \"name\": \"flour\",\n" +
                "  \"quantity\": 1.2,\n" +
                "  \"measurement\": \"kg\"\n" +
                "}";

        Ingredient ingredient = Ingredient.fromJSON(json);

        Parcelable ingredientPacel = Parcels.wrap(ingredient);

        Ingredient copyIngredient = Parcels.unwrap(ingredientPacel);

        Assert.assertEquals(ingredient, copyIngredient);
    }
}
