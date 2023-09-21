package oop.taskTreker.manager.fileSaves;

import oop.taskTreker.manager.history.InMemoryHistoryManager;
import oop.taskTreker.manager.managersTask.InMemoryTaskManager;
import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;
import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    public FileBackedTasksManager(File file) {}

    /**
     * @param file
     * Формат: id,type,name,status,description,epic
     */
    public static FileBackedTasksManager loadFromFile(File file) {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        final FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        try {
            final String csv = Files.readString(file.toPath());
            final String[] lines = csv.split(System.lineSeparator());
            int generatorId = 0;
            List<Long> history = Collections.emptyList();
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    history = CSVFormatter.historyFromString(lines[i + 1]);
                    break;
                }
                final Task task = CSVFormatter.fromString(line);
                final int id = Math.toIntExact(task.getId());
                if (id > generatorId) {
                    generatorId = id;
                }
                taskManager.addNewTask(task);
            }
            for (Map.Entry<Long, Subtask> e : taskManager.subTasksFull().entrySet()) {
                final Subtask subtask = e.getValue();
                final Epic epic = taskManager.epicsFull().get(subtask.getEpicId());
                epic.addSubTasks(subtask.getId());
            }
            for (Long taskId : history) {
                inMemoryHistoryManager.add(taskManager.getTaskId(taskId));

            }
            //taskManager.generatorId = generatorId; не понял зачем оно тут
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
        return taskManager;
    }


    private static class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(IOException message) {
            super(message);
        }
    }
    public void save() {

        try (FileWriter csvOutputFile = new FileWriter("val.csv")) {
            csvOutputFile.write("id,type,name,status,description,epic\n");
            for (Task task : getTasks()) {
                csvOutputFile.write(CSVFormatter.toString(task)+ "\n");
            }
            for (Epic epic : getEpics()) {
                csvOutputFile.write(CSVFormatter.toString(epic) + "\n");
            }
            for (Subtask subtask : getSubTasks()) {
                csvOutputFile.write(CSVFormatter.toString(subtask)+ subtask.getEpicId() + "\n");
            }
            csvOutputFile.write("\n");

            csvOutputFile.write(CSVFormatter.historyToString(new InMemoryHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }


}
