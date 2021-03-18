package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Project... projects);

    @Update
    void update(Project... projects);

    @Delete
    void delete(Project... projects);

    @Query("SELECT * FROM project_table WHERE name = :project_name")
    LiveData<Project> get(String project_name);

    @Query("DELETE FROM project_table")
    void deleteAll();

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getAll();


}