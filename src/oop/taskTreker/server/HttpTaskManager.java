package oop.taskTreker.server;

import oop.taskTreker.manager.Managers;
import oop.taskTreker.manager.fileManager.FileBackedTasksManager;

public class HttpTaskManager extends FileBackedTasksManager {
    private KVClient taskClient;

    //вроде так...
    public HttpTaskManager(String serverUrl) {
        super();
        this.taskClient = (KVClient) Managers.getDefault(serverUrl);
    }
}
