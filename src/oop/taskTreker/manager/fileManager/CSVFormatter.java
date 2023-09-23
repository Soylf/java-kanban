package oop.taskTreker.manager.fileManager;

import oop.taskTreker.manager.historyManager.HistoryManager;
import oop.taskTreker.task.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CSVFormatter {
    CSVFormatter() {}



    public static String toString(Task task) {
        if (task != null) {
            return String.format("%d,%s,%s,%s,%s,", task.getId(),
                    typeOfTaskToString(task.getType()), task.getName(),
                    typeOfStatusToString(task.getStatus()), task.getDesc());
        }
        return null;
    }

    /**
     * @param taskStr Формат id,type,name,status,description,epic
     */
    public static Task fromString (String taskStr) {
        if (taskStr != null) {
            String[] token = taskStr.split(",");
            long id = Long.parseLong(token[0]);
            TaskType type = TaskType.valueOf(token[1]);
            String name = token[2];
            String status = token[3];
            String desc = token[4];
            String val = token[5];
            long epicId = 0;
            if(!token[6].isEmpty()) {
                epicId = Long.parseLong(token[6]);
            }

            switch (type) {
                case EPIC:
                    return new Epic(id,type,name,status,desc,val);

                case TASK:
                    return new Task(id,type,name,status,desc,val);

                case SUBTASK:
                    return new Subtask(id,type,name,status,desc,val,epicId);
            }
        }
        return null;
    }

    //от
    public static String historyToString(HistoryManager manager) {
        StringBuilder viewedIds = new StringBuilder();
        if (!manager.getHistory().isEmpty()) {
            for (Task task : manager.getHistory()) {
                viewedIds.append(task.getId()+1).append(",");
            }
        }
        return viewedIds.toString();
    }
    public static String typeOfTaskToString(TaskType type) {
        for (TaskType value : TaskType.values()) {
            if (type.equals(value)) {
                return type.toString();
            }
        }
        return null;
    }
    public static String typeOfStatusToString(Status status) {
        for (Status value : Status.values()) {
            if (status.equals(value)) {
                return status.toString();
            }
        }
        return null;
    }

    //к
    static List<Long> historyFromString(String historyStr) {
        if (historyStr.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Arrays.stream(historyStr.split(";")).map(Long::parseLong).collect(Collectors.toList());    }
    }
}
