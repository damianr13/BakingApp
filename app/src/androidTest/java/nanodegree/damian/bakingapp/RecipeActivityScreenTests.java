package nanodegree.damian.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.utils.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by robert_damian on 13.06.2018.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityScreenTests {

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule =
            new ActivityTestRule<RecipeActivity>(RecipeActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, RecipeActivity.class);
                    result.putExtra(RecipeActivity.EXTRA_RECIPE, Parcels.wrap(TestUtils.getTestRecipe()));

                    return result;
                }
            };

    @Test
    public void testSeeSteps() {
        onView(withId(R.id.btn_start_cooking)).perform(click());

        onView(withId(R.id.btn_steps_next)).check(matches(isDisplayed()));
    }

}
