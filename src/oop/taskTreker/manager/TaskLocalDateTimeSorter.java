package oop.taskTreker.manager;


import oop.taskTreker.task.Task;

import java.util.Comparator;

public class TaskLocalDateTimeSorter implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if (o1.getStartTime() == null) {
            return 1;
        } else if (o2.getStartTime() == null) {
            return -1;
        } else if (o1.getStartTime().equals(o2.getStartTime())) {
            return 0;
        } else if (o1.getStartTime().isBefore(o2.getStartTime())) {
            return -1;
        } else {
            return 1;
        }
    }
}
