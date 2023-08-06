import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;
import oop.taskTreker.manager.TaskTracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskTracker taskTracker = new TaskTracker();
        Scanner scanner = new Scanner(System.in);


        Task task1 = new Task("task1", "desc1");
        Task task2 = new Task("task2", "desc2");
        taskTracker.addNewTask(task1);
        taskTracker.addNewTask(task2);

        Epic epic1 = new Epic("epic1", "desc1");
        Epic epic2 = new Epic("epic2", "desc2");
        taskTracker.addEpicId(epic1);
        taskTracker.addEpicId(epic2);

        Subtask subtask1 = new Subtask("subtask1", "desc1", epic1.getId());
        taskTracker.addSubtaskId(subtask1);
        Subtask subtask2 = new Subtask("subtask2", "desc2", epic1.getId());
        taskTracker.addSubtaskId(subtask2);
        Subtask subtask3 = new Subtask("subtask3", "desc3",  epic2.getId());
        taskTracker.addSubtaskId(subtask3);

        System.out.println(taskTracker.getTasks());
        System.out.println(taskTracker.getEpics());
        System.out.println(taskTracker.getSubTasks());


        System.out.println("-----");
        System.out.println(taskTracker.getTaskId(task1.getId()));
        System.out.println(taskTracker.getEpicId(subtask1.getEpicId()));
        for (Long num : epic1.getSubTaskIds()){
            System.out.println(taskTracker.getSubtaskId(num));
        }
        System.out.println("-----");


        taskTracker.deleteEpicId(2,epic1);

        System.out.println(taskTracker.getTaskId(task1.getId()));
        System.out.println(taskTracker.getEpicId(subtask1.getEpicId()));
        for (Long num : epic1.getSubTaskIds()){
            System.out.println(taskTracker.getSubtaskId(num));
        }
        System.out.println("-----");
        Task task3 = new Task("task3", "desc3");
        taskTracker.addNewTask(task3);
        Epic epic3 = new Epic("epic3", "desc3");
        taskTracker.addEpicId(epic3);
        Subtask subtask4 = new Subtask("subtask4", "desc4", epic3.getId());
        taskTracker.addSubtaskId(subtask4);

        System.out.println(taskTracker.getTaskId(task3.getId()));
        System.out.println(taskTracker.getEpicId(subtask4.getEpicId()));
        for (Long num : epic3.getSubTaskIds()){
            System.out.println(taskTracker.getSubtaskId(num));
        }
        System.out.println("-----");
        taskTracker.updateSubtaskIN_PROGRESS(subtask4);
        taskTracker.removeEpicById(epic3);
        System.out.println(taskTracker.getTaskId(task3.getId()));
        System.out.println(taskTracker.getEpicId(subtask4.getEpicId()));
        taskTracker.removeSybTaskEpicId(epic3, subtask4);
        for (Long num : epic3.getSubTaskIds()){
            System.out.println(taskTracker.getSubtaskId(num));
        }
        System.out.println("-----");
        taskTracker.deleteAll();
        System.out.println("-----");
        }
    }