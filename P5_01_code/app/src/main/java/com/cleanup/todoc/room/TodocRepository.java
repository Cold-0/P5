package com.cleanup.todoc.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.room.dao.ProjectDao;
import com.cleanup.todoc.room.dao.TaskDao;
import com.cleanup.todoc.room.entity.Project;
import com.cleanup.todoc.room.entity.Task;

import java.util.List;

public class TodocRepository {

    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;
    private LiveData<List<Task>> mAllTasks;
    private LiveData<List<Project>> mAllProjects;


    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public TodocRepository(Application application) {
        TodocRoomDatabase db = TodocRoomDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mProjectDao = db.projectDao();
        mAllTasks = mTaskDao.getAll();
        mAllProjects = mProjectDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Task task) {
        TodocRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }

    public void insert(Project project) {
        TodocRoomDatabase.databaseWriteExecutor.execute(() -> {
            mProjectDao.insert(project);
        });
    }
}
