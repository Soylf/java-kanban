package oop.taskTreker.manager;

import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskTracker{

    private HashMap<Long, Epic> epics = new HashMap<>();
    private HashMap<Long, Subtask> subTasks = new HashMap<>();
    private  HashMap<Long, Task> tasks = new HashMap<>();
    private long generId = 0;

    private Long generateId() {
        return  generId++;
    }
    //
    public void taskPrint(){



    }


    //Создание айдишника
    public long addEpicId(Epic epic) {
        long id = generateId();
        epic.setId(id);
        epics.put(id,epic);
        return id;
    }
    public Long addSubtaskId(Subtask subtask) {
        subtask.setId(generateId());
        if(epics == null) {
            return null;
        }

        subTasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubTasks(subtask.getId());
        updateEpicStat(subtask.getEpicId());
        return subtask.getId();
    }
    public Task addNewTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(),task);
        return task;
    }


    //статусы
    public void updateSubtaskIN_PROGRESS(Subtask subtask){
        Subtask subtask1 = subTasks.get(subtask.getId());
        subtask1.setStatus("IN_PROGRESS");
        updateEpicStat(subtask.getEpicId());
    }
    public void updateSubtaskDONE(Subtask subtask){
        Subtask subtask1 = subTasks.get(subtask.getId());
        subtask1.setStatus("DONE");
        updateEpicStat(subtask.getEpicId());
    }
    private void updateEpicStat(long epicId){
        Epic epic = epics.get(epicId);
        ArrayList<Long> subtask = epic.getSubTaskIds();

        if(subtask.isEmpty()) {
            epic.setStatus("NEW");
            return;
        }

        String status = null;
        for (long sub: subtask) {
            Subtask subtask1 = subTasks.get(sub);

            // 1 - подзадача
            if(status == null) {
                status = subtask1.getStatus();
                continue;
            }
            // 2 - подзадача
            if(status.equals(subtask1.getStatus())
                    && !status.equals("IN_PROGRESS")) {
                epic.setStatus("IN_PROGRESS");
                continue;
            }
            // 3 - подзадача
            if(status.equals(subtask1.getStatus())
                    && !status.equals("DONE")) {
                epic.setStatus("DONE");
                continue;
            }
            return;
        }

        epic.setStatus(status);
    }



    //удаление задач
    public void deleteEpicId(long key){
        if(epics == null) {
            System.out.println("Задачь и так нету...");
        }else {
            epics.remove(key);
            System.out.println("Задача удалена <3");
        }
    }
    public void deleteAll(){
        if(epics == null) {
            System.out.println("Задачь и так нету...");
        }else {
            epics.clear();
            subTasks.clear();
            System.out.println("Все задачи удалены <3");
        }
    }

    // получение
    public void receivingEpicId(long key) {
        if(epics == null) {
            System.out.println("Задачь и так нету...");
        } else {
            System.out.println(epics.get(key));
        }
    }

    //обновление
    public Task removeTaskById(Long id) {
        return tasks.remove(id);
    }
    public Epic removeEpicById(Long id) {
        Epic epic = epics.remove(id);
        for (Subtask subtask : getSubTasks()) {
            removeTaskById(subtask.getId());
        }
        return epic;
    }



    // гетеры
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }


    public List<Subtask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }
}
