package oop.taskTreker.manager;

import oop.taskTreker.manager.historyManager.HistoryManager;
import oop.taskTreker.manager.historyManager.InMemoryHistoryManager;
import oop.taskTreker.server.HttpTaskManager;

public final class Managers {

    private Managers() {
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager() {
        };
    }
    public static TaskManager getDefault(String serverUrl) {
        return new HttpTaskManager(serverUrl);
    }
}