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

        Epic epic1 = new Epic("epic1", "desc1");
        Epic epic2 = new Epic("epic2", "desc2");
        Long EpicId1 = taskTracker.addEpicId(epic1);
        Long EpicId2 = taskTracker.addEpicId(epic2);

        Subtask subtask1 = new Subtask("subtask1", "desc1", EpicId1);
        taskTracker.addSubtaskId(subtask1);
        Subtask subtask2 = new Subtask("subtask2", "desc2", EpicId1);
        taskTracker.addSubtaskId(subtask2);
        Subtask subtask3 = new Subtask("subtask3", "desc3",  EpicId2);
        taskTracker.addSubtaskId(subtask3);

        for (Epic epic : taskTracker.getEpics()) {
            System.out.println(epic);
        }

        for (Task task : taskTracker.getTasks()) {
            System.out.println(task);
        }

        for (Subtask subtask : taskTracker.getSubTasks()) {
            System.out.println(subtask);

        }
                System.out.println("-----");
        taskTracker.updateSubtaskIN_PROGRESS(subtask3);
        System.out.println(subtask3);
        System.out.println(epic2);

        taskTracker.removeTaskById(task1.getId());
        taskTracker.removeEpicById(epic1.getId());

    }
    //Таакс, я понимаю что возможно не правильно но я уже запутался как-это делать
}













/*

    Методы для каждого из типа задач(Задача/Эпик/Подзадача):
            Получение списка всех задач.
            Удаление всех задач. +
            Получение по идентификатору. +-
            Создание. Сам объект должен передаваться в качестве параметра.
            Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
            Удаление по идентификатору. +
    Дополнительные методы:
            Получение списка всех подзадач определённого эпика.
    Управление статусами осуществляется по следующему правилу:+
            Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
            Для эпиков:
            если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.+
            если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.+
            во всех остальных случаях статус должен быть IN_PROGRESS.+

 */
