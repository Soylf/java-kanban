package oop.taskTreker.manager.fileManager;

import oop.taskTreker.manager.historyManager.InMemoryHistoryManager;
import oop.taskTreker.manager.InMemoryTaskManager;
import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;
import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FileBackedTasksManager extends InMemoryTaskManager {
    public FileBackedTasksManager(File file) {}

    public FileBackedTasksManager() {

    }

    public static class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(IOException message) {
            super(message);
        }
    }

    /**
     * @param file
     * Формат: id,type,name,status,description,epic
     */
    public static FileBackedTasksManager loadFromFile(File file) { //сохранение файла
        final FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        try {
            final String csv = Files.readString(file.toPath());
            final String[] lines = csv.split("\n");
            int generatorId = 0;
            List<Long> history = Collections.emptyList();
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    history = CSVFormatter.historyFromString(lines[i + 1]);
                    break;
                }
                Task task = CSVFormatter.fromString(line);
                final long id = task.getId();
                if (id > generatorId) {
                    generatorId = (int) id;
                }
                taskManager.addNewTask(task);
            }
            for (Subtask subtask : taskManager.getSubTasks()) {
                final Epic epic1 = taskManager.getEpicId(subtask.getId());
                epic1.addSubTasks(subtask.getId());
            }
            for (Long taskId : history) {
                taskManager.inMemoryHistoryManager.add(taskManager.getTaskId(taskId));
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
        return taskManager;
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


    //переопределение
    @Override
    public int addSubtaskId(Subtask subtask) {
        super.addSubtaskId(subtask);
        save();
        return 0;
    }

    @Override
    public int addNewTask(Task task) {
        super.addNewTask(task);
        save();
        return 0;
    }

    @Override
    public long addEpicId(Epic epic) {
        super.addEpicId(epic);
        save();
        return 0;
    }

    @Override
    public void deleteEpicId(long key, Epic epic1) {
        super.deleteEpicId(key, epic1);
        save();
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        super.deleteSubtask(subtask);
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteTask(Task task) {
        super.deleteTask(task);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }


}

