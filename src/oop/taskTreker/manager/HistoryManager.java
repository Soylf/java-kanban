package oop.taskTreker.manager;

import oop.taskTreker.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}
