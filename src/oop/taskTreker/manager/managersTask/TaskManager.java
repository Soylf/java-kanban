package oop.taskTreker.manager.managersTask;

import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;

import java.util.List;

public interface TaskManager {
    //Id
     void addEpicId(Epic epic);

     void addSubtaskId(Subtask subtask);

     void addNewTask(Task task);

    //Delete
     void deleteTask(Task task);

     void deleteSubtask(Subtask subtask);

     void deleteEpicId(long key,Epic epic1);

     void deleteAll();

     public void deleteTasks();

     public void deleteSubtasks();

     void deleteEpics();

    //Remove
     void removeEpicById(Epic epic);


     void removeSybTaskEpicId(Epic epic, Subtask subtask);

    //Update
     Task updateTask(Task task);

     Subtask updateSubtask(Subtask subtask);

     Epic updateEpic(Epic epic);

    //Новое

}
