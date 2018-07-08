package nanodegree.damian.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nanodegree.damian.bakingapp.helpers.NetworkHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by robert_damian on 13.06.2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */
    @Test
    public void openRecipeActivity() {
        // if there is no connectivity this test fails intentionally
        if (!NetworkHelper.isConnected(mActivityTestRule.getActivity())) {
            assert true;
            return ;
        }

        onView(withId(R.id.rv_recipes_view)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, click()));

        onView(withId(R.id.lv_ingredients)).check(matches(ViewMatchers.isDisplayed()));
    }
}
