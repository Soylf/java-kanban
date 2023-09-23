package oop.taskTreker.manager;

import oop.taskTreker.manager.historyManager.HistoryManager;
import oop.taskTreker.manager.historyManager.InMemoryHistoryManager;

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