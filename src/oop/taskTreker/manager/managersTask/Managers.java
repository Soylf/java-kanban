package oop.taskTreker.manager.managersTask;

import oop.taskTreker.manager.history.HistoryManager;
import oop.taskTreker.manager.history.InMemoryHistoryManager;
import oop.taskTreker.manager.history.InMemoryTaskManager;

public final class Managers {

    private Managers() {
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager() {
        };
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager() {
        };
    }
}