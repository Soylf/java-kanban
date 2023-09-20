package oop.taskTreker.manager.fileSaves;

import oop.taskTreker.manager.history.InMemoryHistoryManager;
import oop.taskTreker.manager.history.InMemoryTaskManager;
import oop.taskTreker.manager.managersTask.TaskManager;
import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;
import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    public static FileBackedTasksManager loadFile(File file) { // неочень понимаю как реализовать
        TaskManager newTaskManager = new FileBackedTasksManager(file);

        try (Reader reader = new FileReader(String.valueOf(file))) {
            BufferedReader br = new BufferedReader(reader);

            while (!br.readLine().equals("")) {

            }

        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
        return null;
    }
    private static class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(IOException message) {
            super(message);
        }
    }

    /**
     * @param file
     * Формат: id,type,name,status,description,epic
     */
    public FileBackedTasksManager(File file) {

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
            e.printStackTrace();
        }
    }


}
