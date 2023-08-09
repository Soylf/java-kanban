package oop.taskTreker.manager;

import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;

import java.util.List;

public interface TaskManager {
    //Id
    abstract void addEpicId(Epic epic);

    abstract void addSubtaskId(Subtask subtask);

    abstract void addNewTask(Task task);

    //Delete
    abstract void deleteTask(Task task);

    abstract void deleteSubtask(Subtask subtask);

    abstract void deleteEpicId(long key,Epic epic1);

    abstract void deleteAll();

    abstract public void deleteTasks();

    abstract public void deleteSubtasks();

    abstract void deleteEpics();

    //Remove
    abstract void removeEpicById(Epic epic);


    abstract void removeSybTaskEpicId(Epic epic, Subtask subtask);

    //Update
    abstract Task updateTask(Task task);

    abstract Subtask updateSubtask(Subtask subtask);

    abstract Epic updateEpic(Epic epic);

    //Новое

}
