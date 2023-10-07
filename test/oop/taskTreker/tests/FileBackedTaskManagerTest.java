package oop.taskTreker.tests;

import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;
import oop.taskTreker.manager.InMemoryTaskManager;
import oop.taskTreker.manager.fileManager.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static oop.taskTreker.Task.Status.NEW;
import static oop.taskTreker.Task.TaskType.*;
import static oop.taskTreker.Task.TaskType.SUBTASK;
import static oop.taskTreker.manager.fileManager.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    File file = new File("val.csv");
    Task task1;
    Epic epic1;
    Subtask subtask1;

    @BeforeEach
    public void setUp() {
        task1 = new Task("Task1",TASK, "DescriptionTask1", NEW);
        epic1 = new Epic("Epic1",EPIC, "DescriptionEpic1", NEW);
        subtask1 = new Subtask("Subtask1",SUBTASK, "DescriptionSubtask1", NEW,epic1.getId());
    }

    @Test
    public void giveAllTypeOfTasks_whenCreateAndSaveNewCSVFile_thenRestoreFromFile() {
        FileBackedTasksManager testManager = new FileBackedTasksManager();
        testManager.addNewTask(task1);
        testManager.addEpicId(epic1);
        testManager.addSubtaskId(subtask1);
        testManager.addSubtaskId(subtask2);

        testManager.getTaskId(task1.getId());
        testManager.getEpicId(epic1.getId());
        testManager.getSubtaskId(subtask1.getId());

        FileBackedTasksManager restoredManager = loadFromFile(file);
        assertEquals(1, restoredManager.getTasks().size());
        assertEquals(1, restoredManager.getEpics().size());
        assertEquals(2, restoredManager.getSubTasks().size());
        assertEquals(epic1, restoredManager.getEpicId(epic1.getId()));
        assertEquals(subtask2, restoredManager.getSubTasks().get(1));
    }
}