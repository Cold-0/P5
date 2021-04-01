package com.cleanup.todoc.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.cleanup.todoc.ui.taskrecyclerview.TasksRecyclerViewAdapter;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.MatcherAssert;

public class RecyclerViewItemCountAssertion implements ViewAssertion {
    private final Matcher<Integer> matcher;

    public static RecyclerViewItemCountAssertion withItemCount(int expectedCount) {
        return withItemCount(Matchers.is(expectedCount));
    }

    public static RecyclerViewItemCountAssertion withItemCount(Matcher<Integer> matcher) {
        return new RecyclerViewItemCountAssertion(matcher);
    }

    private RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
        this.matcher = matcher;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        TasksRecyclerViewAdapter adapter = (TasksRecyclerViewAdapter) recyclerView.getAdapter();
        assert adapter != null;
        MatcherAssert.assertThat(adapter.getListSize(), matcher);
    }
}