package com.cleanup.todoc.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.repository.room.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task... tasks);

    @Update
    void update(Task... tasks);

    @Delete
    void delete(Task... tasks);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAll();
}