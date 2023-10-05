package oop.taskTreker.tests;

import oop.taskTreker.manager.Managers;
import oop.taskTreker.manager.historyManager.HistoryManager;
import oop.taskTreker.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static oop.taskTreker.task.Status.NEW;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    HistoryManager historyManager;

    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    public void setUp() {
        historyManager = Managers.getDefaultHistory();
        task1 = new Task("T1", "D1", NEW);
        task2 = new Task("T2", "D2", NEW);
        task3 = new Task("T3", "D3", NEW);
        task1.setId(1L);
        task2.setId(2L);
        task3.setId(3L);
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
        historyManager.remove(task1.getId().getEpicId());
        historyManager.remove(task2.getId().getEpicId());
        historyManager.remove(task3.getId().getEpicId());
        assertFalse(historyManager.getHistory().contains(task1));
        assertFalse(historyManager.getHistory().contains(task2));
        assertFalse(historyManager.getHistory().contains(task3));
        assertTrue(historyManager.getHistory().isEmpty());
    }
}