package oop.taskTreker.tests;

import oop.taskTreker.task.Task;
import oop.taskTreker.manager.InMemoryTaskManager;
import oop.taskTreker.manager.Managers;
import oop.taskTreker.manager.historyManager.HistoryManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static oop.taskTreker.task.typeAndStatus.Status.NEW;
import static oop.taskTreker.task.typeAndStatus.TaskType.*;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    HistoryManager historyManager;

    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    public void setUp() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        historyManager = Managers.getDefaultHistory();
        task1 = new Task("T1",TASK, "D1", NEW);
        task2 = new Task("T2",TASK, "D2", NEW);
        task3 = new Task("T3",TASK, "D3", NEW);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
    }

    @Test
    public void whenHistoryEmpty_thenReturnEmptyList() {
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    public void givenTask_whenHistoryFilledWithViewed_thenReturnFilledList() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        assertEquals(3, historyManager.getHistory().size());
        historyManager.add(task3);
        historyManager.add(task2);
        assertEquals(3, historyManager.getHistory().size());
    }

    @Test
    void givenTask_whenRemoveFromHistory_thenRemoveEdgePositions() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        assertTrue(historyManager.getHistory().contains(task1));
        assertTrue(historyManager.getHistory().contains(task2));
        assertTrue(historyManager.getHistory().contains(task3));
        historyManager.remove(task1.getId());
        historyManager.remove(task2.getId());
        historyManager.remove(task3.getId());
        assertFalse(historyManager.getHistory().contains(task1));
        assertFalse(historyManager.getHistory().contains(task2));
        assertFalse(historyManager.getHistory().contains(task3));
        assertTrue(historyManager.getHistory().isEmpty());
    }
}