package com.cleanup.todoc;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.content.Context;
import android.view.View;

import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.utils.DeleteTaskViewAction;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.cleanup.todoc.utils.RecyclerViewItemCountAssertion.withItemCount;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class TaskListInstrumentedTest {

    @SuppressWarnings("deprecation")
    @Rule
    public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    Random rand;

    @Before
    public void initRand() {
        rand = new Random();
    }

    @Test
    public void taskList_CheckAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.openclassroom.mareu", appContext.getPackageName());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void taskList_ShouldNotBeEmpty() {
        onView(AllOf.allOf(ViewMatchers.withId(R.id.list_tasks), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }



    public void AddRandomTask() {
        onView(allOf(withId(R.id.fab_add_task), isDisplayed())).perform(click());
        final String test = "TestTest" + rand.nextInt();
        onView(allOf(withId(R.id.txt_task_name))).perform(replaceText(test));
        onView(withId(android.R.id.button1)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void taskList_DeleteAction_ShouldRemoveItem() {
        Matcher<android.view.View> matcher = allOf(withId(R.id.list_tasks), isDisplayed());
        RecyclerView rv = rule.getActivity().findViewById(R.id.list_tasks);
        int size = rv.getAdapter().getItemCount();

        if (size <= 0) {
            AddRandomTask();
            size++;
        }

        onView(matcher).check(withItemCount(size));
        onView(matcher).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteTaskViewAction()));
        onView(withId(android.R.id.button1)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        onView(matcher).check(withItemCount(size - 1));
    }
//
//    /**
//     * Add meeting and check if it's in the recyclerview (and in the meeting list at same time)
//     */
//    @Test
//    public void meetingList_AddMeeting() {
//        onView(allOf(withId(R.id.add_meeting_button), isDisplayed())).perform(click());
//        final String test = "TestTest";
//        onView(allOf(withId(R.id.meetingSubject))).perform(replaceText(test));
//        onView(allOf(withId(R.id.accept))).perform(click());
//        List<Meeting> meetings = mMeetingApiService.getMeetings();
//
//        boolean found = false;
//        for (Meeting meeting : meetings) {
//            if (meeting.getSubject().equals(test)) {
//                found = true;
//                break;
//            }
//        }
//        assertTrue(found);
//
//        onView(allOf(withId(R.id.meeting_list))).perform(
//                RecyclerViewActions.actionOnItemAtPosition(
//                        meetings.size() - 1, new CheckMeetingSubject(test)
//                )
//        );
//    }
//
//    /**
//     * Check filter work
//     */
//    @Test
//    public void meetingList_FilterMeetingList() {
//        // Click on the filter icon
//        onView(allOf(withId(R.id.filter_menu))).perform(click());
//        onView(allOf(withId(R.id.filterDate))).perform(click());
//        onView(allOf(withClassName(Matchers.equalTo(DatePicker.class.getName())))).perform(PickerActions.setDate(2021, 2, 1));
//        onView(allOf(withText("OK"))).perform(click());
//
//        onView(allOf(withText(R.string.ok))).perform(click());
//        onView(allOf(withId(R.id.meeting_list), isDisplayed())).check(withItemCount(1));
//    }
//
//    /**
//     * Check filter work
//     */
//    @Test
//    public void meetingList_FilterMeetingListReset() {
//        // Click on the filter icon
//        onView(allOf(withId(R.id.filter_menu))).perform(click());
//        onView(allOf(withId(R.id.filterDate))).perform(click());
//        onView(allOf(withClassName(Matchers.equalTo(DatePicker.class.getName())))).perform(PickerActions.setDate(2021, 2, 1));
//        onView(allOf(withText("OK"))).perform(click());
//        onView(allOf(withText(R.string.ok))).perform(click());
//        onView(allOf(withId(R.id.meeting_list), isDisplayed())).check(withItemCount(1));
//        onView(allOf(withId(R.id.filter_menu))).perform(click());
//        onView(allOf(withText(R.string.reset))).perform(click());
//        onView(allOf(withId(R.id.meeting_list), isDisplayed())).check(withItemCount(mMeetingApiService.getMeetings().size()));
//    }
}