package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(tableName = "task_table", foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "name",
        childColumns = "fk_projectName"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "creation_timestamp")
    private long creationTimestamp;

    @ColumnInfo(name = "fk_projectName")
    private String fkProjectName;

    public Task(@NonNull String name, long creationTimestamp, String fkProjectName) {
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
        this.setFkProjectName(fkProjectName);
    }

    ////////////////////////////////////////
    /*  Getter  */
    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getFkProjectName() {
        return fkProjectName;
    }

    ////////////////////////////////////////
    /*  Setter  */
    public void setId(long id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public void setFkProjectName(String fkProjectName) {
        this.fkProjectName = fkProjectName;
    }

    ////////////////////////////////////////
    /*  Comparator  */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
