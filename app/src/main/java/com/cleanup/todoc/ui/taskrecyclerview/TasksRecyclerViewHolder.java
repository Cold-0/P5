package com.cleanup.todoc.ui.taskrecyclerview;

import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.databinding.ItemTaskBinding;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;

class TasksRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final ItemTaskBinding mBinding;
    private final TasksRecyclerViewAdapter.DeleteTaskListener deleteTaskListener;
    private final TaskRepository mTaskRepository;

    TasksRecyclerViewHolder(Application application, @NonNull ItemTaskBinding binding, @NonNull TasksRecyclerViewAdapter.DeleteTaskListener deleteTaskListener) {
        super(binding.getRoot());
        mBinding = binding;
        this.deleteTaskListener = deleteTaskListener;
        mTaskRepository = new TaskRepository(application);
        mBinding.imgDelete.setOnClickListener(view -> {
            final Object tag = view.getTag();
            if (tag instanceof Task) {
                TasksRecyclerViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
            }
        });
    }

    /**
     * Binds a task to the item view.
     */
    void bind(Task task) {
        mBinding.lblTaskName.setText(task.getName());
        mBinding.imgDelete.setTag(task);
        final Project taskProject = mTaskRepository.getProject(task);
        if (taskProject != null) {
            mBinding.imgProject.setColorFilter(taskProject.getColor());
            mBinding.lblProjectName.setText(taskProject.getName());
        } else {
            mBinding.imgProject.setVisibility(View.INVISIBLE);
            mBinding.lblProjectName.setText("");
        }

    }
}
