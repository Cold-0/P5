package com.cleanup.todoc.room.entity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;

@Entity(tableName = "project_table")
public class Project {

    @PrimaryKey
    @ColumnInfo(name = "name")
    @NonNull
    private final String name;

    @ColorInt
    private final int color;

    public Project(@NonNull String name, @ColorInt int color) {
        this.name = name;
        this.color = color;
    }

    // ------------------------------------------------ //
    /*    Getter    */
    @NonNull
    public String getName() {
        return name;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }
}
