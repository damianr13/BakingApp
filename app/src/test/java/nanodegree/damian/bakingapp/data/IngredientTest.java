package nanodegree.damian.bakingapp.data;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by robert_damian on 03.06.2018.
 */

public class IngredientTest {

    @Test
    public void testFromJsonConversion() {
        String json = "{\n" +
                "  \"name\": \"flour\",\n" +
                "  \"quantity\": 1.2,\n" +
                "  \"measurement\": \"kg\"\n" +
                "}";

        Ingredient ingredientFromJSON = Ingredient.fromJSON(json);

        Assert.assertEquals("flour", ingredientFromJSON.getName());
        Assert.assertEquals(1.2, ingredientFromJSON.getQuantity(), .01);
        Assert.assertEquals("kg", ingredientFromJSON.getMeasure());
    }
}
