package com.cleanup.todoc.ui.tasklist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cleanup.todoc.databinding.ItemTaskBinding;
import com.cleanup.todoc.room.entity.Task;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {
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
     * Instantiates a new TasksAdapter.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    public TasksAdapter(@NonNull final List<Task> tasks, @NonNull final DeleteTaskListener deleteTaskListener) {
        this.tasks = tasks;
        this.deleteTaskListener = deleteTaskListener;
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
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        @NonNull ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new TaskViewHolder(binding, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        void onDeleteTask(Task task);
    }
}
