package oop.taskTreker.tests;

import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;
import oop.taskTreker.manager.TaskManager;

import org.junit.jupiter.api.BeforeEach;


import static oop.taskTreker.Task.TypeAndStatus.Status.*;
import static oop.taskTreker.Task.TypeAndStatus.TaskType.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T manager;
    protected Task task1;
    protected Task task2;
    protected Epic epic1;
    protected Epic epic2;
    protected Subtask subtask1;
    protected Subtask subtask2;

    protected void init() {
        task1 = new Task("Task1", TASK, "DescriptionTask1", NEW);
        task2 = new Task("Task2", TASK, "DescriptionTask2", NEW);
        epic1 = new Epic("Epic1", EPIC, "DescriptionEpic1", NEW);
        epic2 = new Epic("Epic2", EPIC, "DescriptionEpic2", NEW);
        subtask1 = new Subtask("Subtask1", SUBTASK, "DescriptionSubtask1", NEW, epic1.getId());
        subtask2 = new Subtask("Subtask2", SUBTASK, "DescriptionSubtask2", NEW, epic2.getId());
    }

    @BeforeEach
    public void setUp() {
        init();
    }

}