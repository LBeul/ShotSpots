package com.lbeul.shotspots_v2;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.lbeul.shotspots_v2.views.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void shouldNavigateToMapActivityAndBack(){
        onView(withId(R.id.textview)).check(matches(isDisplayed()));
        onView(withId(R.id.goToMapView)).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        Espresso.pressBack();
        onView(withId(R.id.textview)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldNavigateToUploadActivityAndBack() {
        onView(withId(R.id.textview)).check(matches(isDisplayed()));
        onView(withId(R.id.goToUpload)).perform(click());
        onView(withId(R.id.load_image_button)).check(matches(isDisplayed()));
        Espresso.pressBack();
        onView(withId(R.id.textview)).check(matches(isDisplayed()));
    }
}