package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TodocRoomDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class ProjectRepository {

    private ProjectDao mProjectDao;
    private LiveData<List<Project>> mAllProjects;

    public ProjectRepository(Application application) {
        TodocRoomDatabase db = TodocRoomDatabase.getDatabase(application);
        mProjectDao = db.projectDao();
        mAllProjects = mProjectDao.getAll();
    }

    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    public void insert(Project project) {
        TodocRoomDatabase.databaseWriteExecutor.execute(() -> {
            mProjectDao.insert(project);
        });
    }
}
