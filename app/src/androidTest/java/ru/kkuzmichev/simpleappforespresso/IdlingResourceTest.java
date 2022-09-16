package ru.kkuzmichev.simpleappforespresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IdlingResourceTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResources() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);
    }

    @After
    public void unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }

    @Test
    public void checkItemInGallery() {
        ViewInteraction navDrawer = onView(withContentDescription("Open navigation drawer"));
        navDrawer
                .check(matches(isDisplayed()))
                .perform(click());

        ViewInteraction gallery = onView(withId(R.id.nav_gallery));
        gallery.perform(click());

        ViewInteraction galleryLayout = onView(withId(R.id.nav_host_fragment_content_main));
        galleryLayout.perform(swipeUp());

        ViewInteraction galleryItem = onView(allOf(
                withId(R.id.item_number),
                withText("7")));
        galleryItem.check(matches(isDisplayed()));
    }
}
