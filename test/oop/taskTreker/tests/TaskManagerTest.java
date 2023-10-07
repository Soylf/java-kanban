package oop.taskTreker.tests;

import java.time.LocalDateTime;

import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;
import oop.taskTreker.manager.InMemoryTaskManager;
import oop.taskTreker.manager.TaskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.time.Month;

import static java.util.Collections.emptyList;
import static oop.taskTreker.Task.Status.*;
import static oop.taskTreker.Task.TaskType.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T manager;
    protected Task task1;
    protected Task task2;
    protected Epic epic1;
    protected Epic epic2;
    protected Subtask subtask1;
    protected Subtask subtask2;

    protected void init() {
        task1 = new Task("Task1",TASK, "DescriptionTask1", NEW);
        task2 = new Task("Task2",TASK, "DescriptionTask2", NEW);
        epic1 = new Epic("Epic1",TASK, "DescriptionEpic1", NEW);
        epic2 = new Epic("Epic2",TASK, "DescriptionEpic2", NEW);
        subtask1 = new Subtask("Subtask1",SUBTASK, "DescriptionSubtask1", NEW,epic1.getId());
        subtask2 = new Subtask("Subtask2",SUBTASK, "DescriptionSubtask2", NEW,epic2.getId());
    }

    @BeforeEach
    public void setUp() {
        init();
    }

    @Test
    public void givenCallRemoveAllTasksMethod_whenRemoveAllTasks_thenAllListsAreEmpty() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addEpicId(epic1);
        manager.addEpicId(epic2);
        manager.addSubtaskId(subtask1);
        manager.addSubtaskId(subtask2);
        assertFalse(manager.getTasks().isEmpty());
        assertFalse(manager.getEpics().isEmpty());
        assertFalse(manager.getSubTasks().isEmpty());
        assertFalse(manager.getPrioritizedTasks().isEmpty());
        manager.deleteTasks();
        assertEquals(emptyList(), manager.getTasks());
        assertEquals(emptyList(), manager.getEpics());
        assertEquals(emptyList(), manager.getSubTasks());
        assertTrue(manager.getPrioritizedTasks().isEmpty());
    }

    @Test
    public void givenIdInParam_whenCallMethodgetTaskId_thenReturnTask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addNewTask(task1);
        manager.getTaskId(task1.getId());
        assertFalse(manager.getPrioritizedTasks().isEmpty());
        assertEquals(task1, manager.getTaskId(task1.getId()));
    }

    @Test
    public void givenIdInParam_whenCallMethodgetEpicId_thenReturnEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        manager.getEpicId(epic1.getId());
        assertFalse(manager.getPrioritizedTasks().isEmpty());
        assertEquals(epic1, manager.getEpicId(epic1.getId()));
    }

    @Test
    public void givenIdInParam_whenCallMethodgetSubtaskId_thenReturnSubtask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        manager.addSubtaskId(subtask1);
        assertFalse(manager.getPrioritizedTasks().isEmpty());
        assertEquals(subtask1, manager.getSubtaskId(subtask1.getId()));
    }

    @Test
    public void givenIdInParam_whenCallMethodRemoveTaskById_thenRemoveTask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addNewTask(task1);
        assertNotNull(manager.getTaskId(task1.getId()));
        manager.removeTaskById(task1.getId());
        assertEquals(emptyList(), manager.getTasks());
        assertNull(manager.getTaskId(task1.getId()));
    }

    @Test
    public void givenIdInParam_whenCallMethodRemoveEpicById_thenRemoveEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        assertNotNull(manager.getEpicId(epic1.getId()));
        manager.removeEpicById(epic1);
        assertEquals(emptyList(), manager.getEpics());
        assertNull(manager.getEpicId(epic1.getId()));
    }

    @Test
    public void givenIdInParam_whenCallMethodRemoveSubtaskById_thenRemoveSubtask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        manager.addSubtaskId(subtask1);
        assertNotNull(manager.getEpicId(epic1.getId()));
        assertNotNull(manager.getSubtaskId(subtask1.getId()));
        manager.removeTaskById(subtask1.getId());
        assertEquals(emptyList(), manager.getSubTasks());
        assertNull(manager.getSubtaskId(subtask1.getId()));
    }

    @Test
    public void givenIdInParam_whenCallMethodGetEpicSubtasksByEpicId_thenReturnListFillOfSubtasks() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        manager.addSubtaskId(subtask1);
        manager.addSubtaskId(subtask2);
        assertNotNull(manager.getEpicId(epic1.getId()));
        assertNotNull(manager.getSubtaskId(subtask1.getId()));
        assertNotNull(manager.getSubtaskId(subtask1.getId()));
        assertFalse(manager.getEpicSubtasksByEpicId(Math.toIntExact(epic1.getId())).isEmpty());
    }

    @Test
    public void whenCallMethodAddNewTask_thenReturnIdAndAddTask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        int id = manager.addNewTask(task1);
        assertEquals(0, id);
        assertEquals(1, manager.getTasks().size());
    }

    @Test
    public void whenCallMethodaddEpicId_thenReturnIdAndAddEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        assertEquals(1, manager.getEpics().size());
    }

    @Test
    public void whenCallMethodaddSubtaskId_thenReturnIdAndAddSubtask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        int id = manager.addSubtaskId(subtask1);
        assertEquals(1, id);
        assertEquals(1, manager.getSubTasks().size());
    }

    @Test
    public void giveNewTaskInParam_whenCallMethodUpdateTask_thenReplaceOldTaskWithNewTask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addNewTask(task1);
        for (Task task : manager.getTasks()) {
            assertEquals(task1, task);
        }
        for (Task prioritizedTask : manager.getPrioritizedTasks()) {
            assertEquals(task1, prioritizedTask);
        }
        Task test = new Task("Test",TASK, "TestDescription", NEW);
        test.setId(task1.getId());
        manager.updateTask(test);
        for (Task task : manager.getTasks()) {
            assertEquals(test, task);
        }
        for (Task prioritizedTask : manager.getPrioritizedTasks()) {
            assertEquals(test, prioritizedTask);
        }
    }

    @Test
    public void giveNewEpicInParam_whenCallMethodUpdateEpic_thenReplaceOldEpicWithNewEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        for (Epic epic : manager.getEpics()) {
            assertEquals(epic1, epic);
        }
        for (Task prioritizedTask : manager.getPrioritizedTasks()) {
            assertEquals(epic1, prioritizedTask);
        }
        Epic test = new Epic("Test",EPIC, "TestDescription", NEW);
        test.setId(epic1.getId());
        manager.updateEpic(test);
        for (Epic epic : manager.getEpics()) {
            assertEquals(test, epic);
        }
        for (Task prioritizedTask : manager.getPrioritizedTasks()) {
            assertEquals(test, prioritizedTask);
        }
    }

    @Test
    public void giveNewSubtaskInParam_whenCallMethodUpdateSubtask_thenReplaceOldSubtaskWithNewSubtask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addNewTask(task1);
        manager.addEpicId(epic1);
        manager.addSubtaskId(subtask1);
        assertEquals(subtask1, manager.getSubTasks().get(0));
        assertEquals(3, manager.getPrioritizedTasks().size());
        Subtask test = new Subtask("Test", SUBTASK,"TestDescription", NEW, epic1.getId());
        test.setId(subtask1.getId());
        manager.updateSubtask(test);
        for (Subtask subtask : manager.getSubTasks()) {
            assertEquals(test, subtask);
        }
        assertTrue(manager.getPrioritizedTasks().contains(test));
    }

    @Test
    public void giveId_whenUpdateStatus_thenChangeEpicStatus() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic testEpic1 = new Epic("Test1",EPIC, "Description", NEW);
        manager.addEpicId(testEpic1);
        assertEquals(NEW, testEpic1.getStatus());
        Subtask testSubtask1 = new Subtask("Test2",SUBTASK, "Description", NEW,epic1.getId());
        manager.addSubtaskId(testSubtask1);
        assertEquals(NEW, testEpic1.getStatus());
        Subtask testSubtask2 = new Subtask("Test4",SUBTASK, "Description", IN_PROGRESS,epic2.getId());
        manager.addSubtaskId(testSubtask2);
        assertEquals(IN_PROGRESS, testEpic1.getStatus());
        testSubtask1.setStatus(String.valueOf(DONE));
        testSubtask2.setStatus(String.valueOf(DONE));
        manager.updateSubtaskDONE(testEpic1.getId());
        assertEquals(DONE, testEpic1.getStatus());
    }

    @Test
    public void whenGetTasks_thenReturnListFillOfTasks() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        assertEquals(emptyList(), manager.getTasks());
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        assertEquals(2, manager.getTasks().size());
    }

    @Test
    public void whenGetEpics_thenReturnListFillOfEpics() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        assertEquals(emptyList(), manager.getEpics());
        manager.addEpicId(epic1);
        manager.addEpicId(epic2);
        assertEquals(2, manager.getEpics().size());
    }

    @Test
    public void whengetSubTasks_thenReturnListFillOfSubtasks() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpicId(epic1);
        assertEquals(emptyList(), manager.getSubTasks());
        manager.addSubtaskId(subtask1);
        manager.addSubtaskId(subtask2);
        assertEquals(2, manager.getSubTasks().size());
    }

    @Test
    public void whenGetPrioritizedTasks_thenReturnTreeSetFillOfTasks() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        task1.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 4, 21, 59));
        task2.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 4, 22, 30));
        epic1.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 3, 20, 10));
        assertTrue(manager.getPrioritizedTasks().isEmpty());
        manager.addNewTask(task1);
        assertEquals(1, manager.getPrioritizedTasks().size());
        manager.addNewTask(task2);
        manager.addEpicId(epic1);
        assertEquals(3, manager.getPrioritizedTasks().size());
        assertEquals(epic1, manager.getPrioritizedTasks().get(0));
    }

    @Test
    public void givenStartTimeAndDuration_whenAddSubtask_thenCalculateTimeFieldsOfEpic() {
        manager.addEpicId(epic1);
        subtask1.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 5, 10, 1));
        subtask1.setDuration(Duration.ofMinutes(59));
        manager.addSubtaskId(subtask1);
        assertEquals(LocalDateTime.of(2023, Month.OCTOBER, 5, 10, 1), epic1.getStartTime());
        assertEquals(Duration.ofMinutes(59), epic1.getDuration());

        subtask2.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 5, 11, 0));
        subtask2.setDuration(Duration.ofMinutes(30));
        manager.addSubtaskId(subtask2);
        assertEquals(LocalDateTime.of(2023, Month.OCTOBER, 5, 10, 1), epic1.getStartTime());
        assertEquals(Duration.ofMinutes(89), epic1.getDuration());
        assertFalse(manager.isIntersection(subtask1));

        task1.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 5, 10, 20));
        task1.setDuration(Duration.ofMinutes(10));
        manager.addNewTask(task1);
        assertFalse(manager.isIntersection(task1));

        manager.calculateEndTimeForEpic(epic1.getId());
        assertEquals(LocalDateTime.of(2023, Month.OCTOBER, 5, 11, 30), epic1.getEndTime());
    }
}