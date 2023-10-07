package oop.taskTreker.manager;



import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;

import java.util.List;

public interface TaskManager {
    //Id
     long addEpicId(Epic epic);

     int addSubtaskId(Subtask subtask);

     int addNewTask(Task task);

    void calculateDurationTimeForEpic(Integer epicId);

    void calculateEndTimeForEpic(Integer epicId);

    void calculateStartTimeForEpic(Integer epicId);

    List<Subtask> getEpicSubtasksByEpicId(Integer uniqueId);

    boolean isIntersection(Task task);

    void calculateEndTimeForEpic(Long epicId);

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

    List<Task> getPrioritizedTasks();

    void removeTaskById(Long uniqueId);

    //Новое

}
