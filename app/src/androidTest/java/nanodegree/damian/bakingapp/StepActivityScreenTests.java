package nanodegree.damian.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import nanodegree.damian.bakingapp.data.Recipe;
import nanodegree.damian.bakingapp.utils.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by robert_damian on 13.06.2018.
 */
@RunWith(AndroidJUnit4.class)
public class StepActivityScreenTests {

    private static final Recipe sRecipe = TestUtils.getTestRecipe();

    @Rule
    public ActivityTestRule<StepActivity> mActivityTestRule =
            new ActivityTestRule<StepActivity>(StepActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, StepActivity.class);
                    result.putExtra(StepActivity.EXTRA_RECIPE, Parcels.wrap(sRecipe));
                    result.putExtra(StepActivity.EXTRA_STEP_INDEX, 0);

                    return result;
                }
            };

    @Test
    public void testDisplayedInfo() {
        onView(withId(R.id.tv_step_description))
                .check(matches(withText(sRecipe.getStepList().get(0).getDescription())));
    }

    @Test
    public void testNexStep() {
        onView(withId(R.id.btn_steps_next)).perform(ViewActions.click());

        onView(withId(R.id.tv_step_description))
                .check(matches(withText(sRecipe.getStepList().get(1).getDescription())));
    }
}
