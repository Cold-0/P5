package com.cleanup.todoc;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;

import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.utils.DeleteTaskViewAction;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.cleanup.todoc.utils.TestUtils.withRecyclerView;
import static com.cleanup.todoc.utils.RecyclerViewItemCountAssertion.withItemCount;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @SuppressWarnings("deprecation")
    @Rule
    public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    private Random rand;

    public int GetRandomInt(int max) {
        if (rand == null)
            rand = new Random();

        return rand.nextInt(max);
    }

    void AddRandomTask() {
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example" + GetRandomInt(99999)));
        onView(withId(android.R.id.button1)).perform(click());
    }

    void DeleteRandomTask(int size) {
        onView(allOf(withId(R.id.list_tasks), isDisplayed())).perform(
                RecyclerViewActions.actionOnItemAtPosition(
                        GetRandomInt(size),
                        new DeleteTaskViewAction()
                )
        );
    }

    void DeleteAllTask(int size) {
        if (size != 0)
            for (int i = 0; i < size; i++) {
                onView(withId(R.id.list_tasks)).perform(
                        RecyclerViewActions.actionOnItemAtPosition(
                                GetRandomInt(size),
                                new DeleteTaskViewAction()
                        )
                );
            }
    }

    @Test
    public void TestCheckAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.cleanup.todoc", appContext.getPackageName());
    }

    @Test
    public void TestGoodCount() {
        Matcher<View> matcher = allOf(withId(R.id.list_tasks), isDisplayed());
        RecyclerView rv = rule.getActivity().findViewById(R.id.list_tasks);
        onView(matcher).check(withItemCount(rv.getAdapter().getItemCount()));
    }

    @Test
    public void TestAddAndRemoveTask() {
        MainActivity activity = rule.getActivity();
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        int item_count = listTasks.getAdapter().getItemCount();

        AddRandomTask();
        item_count++;

        // Check that recyclerView is displayed
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        // Check item count
        onView(allOf(withId(R.id.list_tasks), isDisplayed())).check(withItemCount(item_count));

        DeleteRandomTask(item_count);

        // Check item count
        //if (!(listTasks.getVisibility() == View.GONE) && item_count > 0)
        onView(allOf(withId(R.id.list_tasks), isDisplayed())).check(withItemCount(item_count));
    }

    @Test
    public void TestSortTasks() {
        DeleteAllTask(((RecyclerView) rule.getActivity().findViewById(R.id.list_tasks)).getAdapter().getItemCount());

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort alphabetical
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
    }
}
