package com.cleanup.todoc;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocRoomDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("deprecation")
@RunWith(AndroidJUnit4.class)
public class DaoInstrumentedTest {

    private TodocRoomDatabase database;

    @Before
    public void initDatabase() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDatabase() {
        database.close();
    }

    @Test
    public void TestInsertTaskAndProject() {

        // Test insert and get
        Project test_project = new Project("Test01", 0xffff00ff);
        database.projectDao().insert(test_project);
        assertEquals(test_project.getName(), database.projectDao().get("Test01").getName());

        // Test insert and get
        Task test_task = new Task("Test05", 0, "Test01");
        database.taskDao().insert(test_task);
        assertEquals(test_task.getName(), database.taskDao().getByName("Test05").getName());
    }


}
