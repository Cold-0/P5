package com.cleanup.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.ActivityMainBinding;
import com.cleanup.todoc.databinding.DialogAddTaskBinding;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksRecyclerViewAdapter.DeleteTaskListener {
    private ActivityMainBinding mBinding;

    private TaskViewModel mTaskViewModel;
    private ProjectViewModel mProjectViewModel;

    private List<Task> mTaskList;
    private List<Project> mProjectList;

    private final TasksRecyclerViewAdapter mTasksAdapter = new TasksRecyclerViewAdapter(mTaskList, this);

    @NonNull
    private SortMethod mSelectedSortMethod = SortMethod.NONE;

    @Nullable
    public AlertDialog mAlertDialog = null;
    private DialogAddTaskBinding mDialogAddTaskBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mTaskViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(TaskViewModel.class);
        mProjectViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ProjectViewModel.class);

        mTaskViewModel.getAllTasks().observe(this, tasks -> {
            mTaskList = tasks;
            mTasksAdapter.updateTasks(tasks);
        });

        mProjectViewModel.getAllProjects().observe(this, projects -> {
            mProjectList = projects;
        });

        mBinding.listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.listTasks.setAdapter(mTasksAdapter);

        mBinding.fabAddTask.setOnClickListener(view -> showAddTaskDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            mSelectedSortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            mSelectedSortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            mSelectedSortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            mSelectedSortMethod = SortMethod.RECENT_FIRST;
        }

        updateTasks();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(Task task) {
        mTaskList.remove(task);
        updateTasks();
    }

    //////////////////////////////////////
    /*   Task   */
    private void addTask(@NonNull Task task) {
        mTaskList.add(task);
        updateTasks();
    }

    private void updateTasks() {
        if (mTaskList.size() == 0) {
            mBinding.lblNoTask.setVisibility(View.VISIBLE);
            mBinding.listTasks.setVisibility(View.GONE);
        } else {
            mBinding.lblNoTask.setVisibility(View.GONE);
            mBinding.listTasks.setVisibility(View.VISIBLE);
            switch (mSelectedSortMethod) {
                case ALPHABETICAL:
                    Collections.sort(mTaskList, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(mTaskList, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(mTaskList, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(mTaskList, new Task.TaskOldComparator());
                    break;

            }
            mTasksAdapter.updateTasks(mTaskList);
        }
    }

    //////////////////////////////////////
    /*   Add Task Dialog   */
    private void showAddTaskDialog() {
        mDialogAddTaskBinding = DialogAddTaskBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog);
        builder.setView(mDialogAddTaskBinding.getRoot());
        builder.setTitle(R.string.add_task);
        builder.setPositiveButton(R.string.add, null);
        builder.setOnDismissListener(dialogInterface -> {
            mAlertDialog = null;
            mDialogAddTaskBinding = null;
        });
        mAlertDialog = builder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        mAlertDialog.setOnShowListener(dialogInterface -> {
            Button button = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonAddTaskDialog(mAlertDialog));
        });

        mAlertDialog.show();

        populateProjectSpinnerAddTaskDialog();
    }

    private void onPositiveButtonAddTaskDialog(DialogInterface dialogInterface) {
        // If dialog is open
        if (mDialogAddTaskBinding != null) {
            // Get the name of the task
            String taskName = mDialogAddTaskBinding.txtTaskName.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (mDialogAddTaskBinding.projectSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) mDialogAddTaskBinding.projectSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                mDialogAddTaskBinding.txtTaskName.setError(getString(R.string.empty_task_name));
            }

            // If both project and name of the task have been set
            else if (taskProject != null) {
                Task task = new Task(taskName, new Date().getTime(), taskProject.getName());
                addTask(task);
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else
                dialogInterface.dismiss();
        }
        // If dialog is already closed
        else
            dialogInterface.dismiss();
    }

    private void populateProjectSpinnerAddTaskDialog() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mProjectList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDialogAddTaskBinding.projectSpinner.setAdapter(adapter);
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        ALPHABETICAL,
        ALPHABETICAL_INVERTED,
        RECENT_FIRST,
        OLD_FIRST,
        NONE
    }
}
