package com.cleanup.todoc.ui.taskrecyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.databinding.ItemTaskBinding;
import com.cleanup.todoc.model.Task;

class TasksRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final ItemTaskBinding mBinding;
    private final TasksRecyclerViewAdapter.DeleteTaskListener deleteTaskListener;

    TasksRecyclerViewHolder(@NonNull ItemTaskBinding binding, @NonNull TasksRecyclerViewAdapter.DeleteTaskListener deleteTaskListener) {
        super(binding.getRoot());
        mBinding = binding;
        this.deleteTaskListener = deleteTaskListener;

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
