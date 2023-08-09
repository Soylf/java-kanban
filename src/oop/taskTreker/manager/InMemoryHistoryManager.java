package oop.taskTreker.manager;

import oop.taskTreker.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> historyOfViews = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        historyOfViews.add(task);
        if (historyOfViews.size() >= 10) {
            historyOfViews.remove(0);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyOfViews;
    }
}
