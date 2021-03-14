package com.cleanup.todoc.repository.room.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.repository.TodocRepository;
import com.cleanup.todoc.repository.room.entity.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private final TodocRepository mRepository;

    private final LiveData<List<Task>> mAllTasks;

    public TaskViewModel(Application application) {
        super(application);
        mRepository = new TodocRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void insert(Task task) {
        mRepository.insert(task);
    }
}