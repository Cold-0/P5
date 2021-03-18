package com.cleanup.todoc.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.repository.TodocRepository;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private final TodocRepository mRepository;

    private final LiveData<List<Project>> mAllProjects;

    public ProjectViewModel(Application application) {
        super(application);
        mRepository = new TodocRepository(application);
        mAllProjects = mRepository.getAllProjects();
    }

    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    public void insert(Project project) {
        mRepository.insert(project);
    }
}