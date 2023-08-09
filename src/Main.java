import oop.taskTreker.manager.InMemoryHistoryManager;
import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;
import oop.taskTreker.manager.InMemoryTaskManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskTracker = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
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
        Subtask subtask3 = new Subtask("subtask3", "desc3", epic2.getId());
        taskTracker.addSubtaskId(subtask3);

        System.out.println(taskTracker.getTasks());
        System.out.println(taskTracker.getEpics());
        System.out.println(taskTracker.getSubTasks());


        System.out.println("-----");
        taskTracker.updateSubtaskDONE(subtask1);

        System.out.println(taskTracker.getTaskId(task1.getId()));
        System.out.println(taskTracker.getEpicId(subtask1.getEpicId()));
        for (Long num : epic1.getSubTaskIds()) {
            System.out.println(taskTracker.getSubtaskId(num));
        }
        System.out.println(taskTracker.getEpicSubtasks(epic1.getId()));
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);

        System.out.println(inMemoryHistoryManager.getHistory());
        System.out.println("-----");


    }
}