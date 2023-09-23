package oop.taskTreker.manager.historyManager;

import oop.taskTreker.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
     void remove(long id);
    List<Task> getHistory();
}
