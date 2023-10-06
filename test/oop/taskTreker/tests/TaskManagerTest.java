package oop.taskTreker.tests;

import oop.taskTreker.manager.InMemoryTaskManager;
import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Status;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskManagerTest<I> {

    protected InMemoryTaskManager manager;
    protected Task task1;
    protected Epic epic1;
    protected Subtask subtask1;
    protected Subtask subtask2;

    @BeforeEach
    public void setUp() {
        manager = new InMemoryTaskManager();
        task1 = new Task("Task1", "DescriptionTask1", Status.NEW);
        epic1 = new Epic("Epic1", "DescriptionEpic1", Status.NEW);
        subtask1 = new Subtask("Subtask1", "DescriptionSubtask1", Status.NEW);
        subtask2 = new Subtask("Subtask2", "DescriptionSubtask2", Status.NEW);
    }

    @Test
    public void testCalculateTimeFieldsOfEpicWhenAddSubtask() {
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
    }

    @Test
    public void testIntersectionWhenAddNonIntersectingTask() {
        task1.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 5, 10, 20));
        task1.setDuration(Duration.ofMinutes(10));
        manager.addNewTask(task1);
        assertFalse(manager.isIntersection(task1));
    }

    @Test
    public void testCalculateEndTimeForEpic() {
        manager.addEpicId(epic1);
        subtask1.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 5, 10, 1));
        subtask1.setDuration(Duration.ofMinutes(59));
        manager.addSubtaskId(subtask1);
        subtask2.setStartTime(LocalDateTime.of(2023, Month.OCTOBER, 5, 11, 0));
        subtask2.setDuration(Duration.ofMinutes(30));
        manager.addSubtaskId(subtask2);
        manager.calculateEndTimeForEpic(epic1.getId());
        assertEquals(LocalDateTime.of(2023, Month.OCTOBER, 5, 11, 30), epic1.getEndTime());
    }
}