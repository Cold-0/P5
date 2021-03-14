package com.cleanup.todoc.ui.tasklist;

import android.app.Application;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.databinding.ItemTaskBinding;
import com.cleanup.todoc.room.TodocRoomDatabase;
import com.cleanup.todoc.room.entity.Project;
import com.cleanup.todoc.room.entity.Task;

class TaskViewHolder extends RecyclerView.ViewHolder {
    private final ItemTaskBinding mBinding;
    private final TasksAdapter.DeleteTaskListener deleteTaskListener;

    TaskViewHolder(@NonNull ItemTaskBinding binding, @NonNull TasksAdapter.DeleteTaskListener deleteTaskListener) {
        super(binding.getRoot());
        mBinding = binding;
        this.deleteTaskListener = deleteTaskListener;

        mBinding.imgDelete.setOnClickListener(view -> {
            final Object tag = view.getTag();
            if (tag instanceof Task) {
                TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
            }
        });
    }

    /**
     * Binds a task to the item view.
     */
    void bind(Task task) {
        mBinding.lblTaskName.setText(task.getName());
        mBinding.imgDelete.setTag(task);

//        //final Project taskProject =
//        if (taskProject != null) {
//            mBinding.imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
//            mBinding.lblProjectName.setText(taskProject.getName());
//        } else {
//            mBinding.imgProject.setVisibility(View.INVISIBLE);
//            mBinding.lblProjectName.setText("");
//        }

    }
}
