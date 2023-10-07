package oop.taskTreker.tests;


import oop.taskTreker.manager.fileManager.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;


import static oop.taskTreker.manager.fileManager.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    File file = new File("val.csv");

    @BeforeEach
    public void setUp() {
        manager = new FileBackedTasksManager();
        init();
    }

    @Test
    public void giveAllTypeOfTasks_whenCreateAndSaveNewCSVFile_thenRestoreFromFile() {
        FileBackedTasksManager testManager = new FileBackedTasksManager();
        testManager.addNewTask(task1);
        testManager.addEpicId(epic1);
        testManager.addSubtaskId(subtask1);
        testManager.addSubtaskId(subtask2);


        testManager.getSubtaskId(subtask1.getId());


        FileBackedTasksManager restoredManager = loadFromFile(file);
        assertEquals(1, restoredManager.getTasks().size());
        assertEquals(1, restoredManager.getEpics().size());
        assertEquals(2, restoredManager.getSubTasks().size());
        assertEquals(epic1, restoredManager.getEpicId(epic1.getId()));
        assertEquals(subtask2, restoredManager.getSubTasks().get(1));
    }
}