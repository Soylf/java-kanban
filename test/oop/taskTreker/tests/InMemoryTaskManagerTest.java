package oop.taskTreker.tests;

import oop.taskTreker.manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sun.org.apache.xml.internal.security.Init.init;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp() {
        manager = new InMemoryTaskManager();
        init();
    }

    @Test
    public void whenCallGenerateId_thenReturnIntegerPlusOne() {
        manager.addNewTask(task1);
        assertEquals(0, task1.getId());
        manager.addEpicId(epic1);
        assertEquals(1, epic1.getId());
    }
    //Попытался исправить, но что-то пошло не так
    //я не понимаю почему метод FileBackedTaskManagerTest не работает как я хочу
}
