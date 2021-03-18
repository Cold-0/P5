package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocRoomDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();

    private static volatile TodocRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ProjectDao projectdao = INSTANCE.projectDao();
                TaskDao taskdao = INSTANCE.taskDao();

                projectdao.deleteAll();
                taskdao.deleteAll();

                projectdao.insert(
                        new Project("Tartampion", 0xff0000),
                        new Project("Lucidia", 0x00ff00),
                        new Project("Circus", 0x0000ff)
                );

                taskdao.insert(
                        new Task("Ajouter un header sur le site", System.currentTimeMillis() / 1000, "Tartampion"),
                        new Task("Modifier la couleur des textes", System.currentTimeMillis() / 1000, "Tartampion"),
                        new Task("Appeler le client", System.currentTimeMillis() / 1000, "Lucidia"),
                        new Task("Intégrer Google Analytics", System.currentTimeMillis() / 1000, "Circus"),
                        new Task("Ajouter un header sur le site", System.currentTimeMillis() / 1000, "Lucidia")
                );

            });
        }
    };

    public static TodocRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodocRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocRoomDatabase.class, "todoc_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
