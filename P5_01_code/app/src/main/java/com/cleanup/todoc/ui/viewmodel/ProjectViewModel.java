package com.cleanup.todoc.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private final ProjectRepository mProjectRepository;

    private final LiveData<List<Project>> mAllProjects;

    public ProjectViewModel(Application application) {
        super(application);
        mProjectRepository = new ProjectRepository(application);
        mAllProjects = mProjectRepository.getAllProjects();
    }

    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    public void insert(Project project) {
        mProjectRepository.insert(project);
    }
}