package com.cleanup.todoc.ui.taskrecyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cleanup.todoc.databinding.ItemTaskBinding;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;

import java.util.Collections;
import java.util.List;

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewHolder> {
    @NonNull
    Application application;

    /**
     * The list of tasks the adapter deals with
     */
    @NonNull
    private List<Task> tasks;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksRecyclerViewAdapter.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    public TasksRecyclerViewAdapter(@NonNull Application application, @NonNull final List<Task> tasks, @NonNull final DeleteTaskListener deleteTaskListener) {
        this.tasks = tasks;
        this.deleteTaskListener = deleteTaskListener;
        this.application = application;
    }

    public int getListSize() {
        return tasks.size();
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    public void updateTasks(@NonNull final List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TasksRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        @NonNull ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new TasksRecyclerViewHolder(application, binding, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksRecyclerViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void sortList(MainActivity.SortMethod sortMethod) {
        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(tasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(tasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(tasks, new Task.TaskOldComparator());
                break;

        }
        notifyDataSetChanged();
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        void onDeleteTask(Task task);
    }
}
