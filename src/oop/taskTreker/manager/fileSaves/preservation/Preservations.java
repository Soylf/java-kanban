package oop.taskTreker.manager.fileSaves.preservation;

import oop.taskTreker.manager.fileSaves.FileBackedTasksManager;
import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;

import java.io.File;

public class Preservations extends FileBackedTasksManager {
    public Preservations(File file) {
        super(file);
    }

    @Override
    public void addSubtaskId(Subtask subtask) {
        super.addSubtaskId(subtask);
        save();
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    @Override
    public void addEpicId(Epic epic) {
        super.addEpicId(epic);
        save();
    }
}
