package oop.taskTreker.tests;

import oop.taskTreker.manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp() {
        init();
    }

    @Test
    public void whenCallGenerateId_thenReturnIntegerPlusOne() {
        int id1 = manager.addNewTask(task1);
        assertEquals(0, id1);
        long id2 = manager.addEpicId(epic1);
        assertEquals(1, id2);
    }
    //неоч понял

    //Более эффективно не строить отсортированный список каждый раз, а добавлять и удалять из него в момент добавления, удаления задач и подзадач в менеджер. Т.е.
    // добавление или удаление элемента в отсортированнный список эффективней,
    // чем построение его с нуля. И далее в методе проверки пересечения периодов уже использовать готовый отсортированный список.
}
