package com.example.moviesapp;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.moviesapp.view.activity.HomeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class TablayoutTest {
    HomeActivity mActivity=null;
    @Rule
   public  ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() throws Exception{
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }


    @Test
    public void checkTabLayoutDisplayed() {
        Espresso.onView(withId(R.id.tablayout)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void swipePage() {
        Espresso.onView(withId(R.id.viewpager)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.viewpager)).perform(swipeLeft());
    }

    @Test
    public void checkTabSwitch() {
        // I'd like to switch to a tab (test2) and check that the switch happened
        Espresso.onView(allOf(withText("Favourite Items"), isDescendantOfA(withId(R.id.tablayout))))
                .perform(click())
                .check(matches(isDisplayed()));



    }


    @After
    public void tearDown() throws Exception {
        mActivity=null;
    }
}
