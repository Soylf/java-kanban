package oop.taskTreker.converter;

import oop.taskTreker.task.typeAndStatus.Status;
import oop.taskTreker.task.typeAndStatus.Type;
import oop.taskTreker.manager.historyManager.HistoryManager;
import oop.taskTreker.task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CSVFormatter {
    CSVFormatter() {}



    public static String toString(Task task) {
        try {
            return String.format("%d,%s,%s,%s,%s,%s,%s,",
                    task.getId(),
                    task.getType(),
                    task.getName(),
                    task.getStatus(),
                    task.getDesc(),
                    task.getStartTime(),
                    task.getDuration());
        }
        catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public static Task stringToTask (String line) {
        try {
            if (!line.isBlank() && !line.isEmpty()) {
                String[] tokens = line.split(",");
                Type type = Type.valueOf(tokens[1]);
                for (int i = 0; i < tokens.length; i++) {
                    switch (type) {
                        case TASK:
                            Task task = new Task();
                            task.setId(Long.parseLong(tokens[0]));
                            task.setName(tokens[2]);
                            task.setStatus(tokens[3]);
                            task.setDesc(tokens[4]);
                            if (!tokens[5].equals("null") && !tokens[6].equals("null")) {
                                task.setStartTime(LocalDateTime.parse(tokens[5]));
                                task.setDuration(Duration.parse(tokens[6]));
                            return task;
                        }
                        case EPIC:
                            Epic epic = new Epic();
                            epic.setId(Long.parseLong(tokens[0]));
                            epic.setName(tokens[2]);
                            epic.setStatus(tokens[3]);
                            epic.setDesc(tokens[4]);
                            if (!tokens[5].equals("null") && !tokens[6].equals("null")) {
                                epic.setStartTime(LocalDateTime.parse(tokens[5]));
                                epic.setDuration(Duration.parse(tokens[6]));
                            }
                            return epic;

                        case SUBTASK:
                                Subtask subtask = new Subtask();
                                subtask.setId(Long.parseLong(tokens[0]));
                                subtask.setName(tokens[2]);
                                subtask.setStatus(tokens[3]);
                                subtask.setDesc(tokens[4]);
                                if (!tokens[5].equals("null") && !tokens[6].equals("null")) {
                                    subtask.setStartTime(LocalDateTime.parse(tokens[5]));
                                    subtask.setDuration(Duration.parse(tokens[6]));
                                }
                                subtask.setEpicId(Long.parseLong(tokens[7]));

                                return subtask;
                    }
                }
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }return null;
    }


    public static String historyToString (HistoryManager manager) {
        StringBuilder viewedIds = new StringBuilder();
        if (!manager.getHistory().isEmpty()) {
            for (Task task : manager.getHistory()) {
                viewedIds.append(task.getId()+1).append(",");
            }
        }
        return viewedIds.toString();
    }
    public static String typeOfTaskToString(Type type) {
        for (Type value : Type.values()) {
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


    public static List<Long> historyFromString(String historyStr) {
        if (historyStr.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Arrays.stream(historyStr.split(";"))
                    .map(Long::parseLong).collect(Collectors.toList());
        }
    }
}
