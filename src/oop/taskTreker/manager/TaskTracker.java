package oop.taskTreker.manager;

import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Status;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskTracker{

    IdGenerator generateId = new IdGenerator();
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long, Subtask> subTasks = new HashMap<>();
    private final HashMap<Long, Task> tasks = new HashMap<>();







    //Создание айдишника
    public void addEpicId(Epic epic) {

        epic.setId(generateId.generateId());
        epics.put(epic.getId(),epic);

    }

    public void addSubtaskId(Subtask subtask) {

        subtask.setId(generateId.generateId());
        subTasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubTasks(subtask.getId());
        updateEpicStat(subtask.getEpicId());

    }
    public void addNewTask(Task task) {

        task.setId(generateId.generateId());
        tasks.put(task.getId(),task);


    }


    //статусы
    public void updateSubtaskIN_PROGRESS(Subtask subtask){
        Subtask subtask1 = subTasks.get(subtask.getId());
        subtask1.setStatus(String.valueOf(Status.IN_PROGRESS));
        updateEpicStat(subtask.getEpicId());
    }
    public void updateSubtaskDONE(Subtask subtask){
        Subtask subtask1 = subTasks.get(subtask.getId());
        subtask1.setStatus(String.valueOf(Status.DONE));
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
    public void deleteEpicId(long key,Epic epic1){
        epics.remove(key);

        for (Long num : epic1.getSubTaskIds()){
            subTasks.remove(num);
        }
        System.out.println("Задача удалена <3");
    }

    public void deleteAll(){
        epics.clear();
        tasks.clear();
        subTasks.clear();
        System.out.println("Все задачи удалены <3");
    }

    //обновление


    public void removeEpicById(Epic epic) {
        epic.setId(generateId.generateId());
    }
    public void  removeSybTaskEpicId(Epic epic,Subtask subtask){
        subtask.setEpicId(epic.getId());
    }



    // гетеры
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }
    public Epic getEpicId(Long id) {
        return epics.get(id);
    }


    public List<Subtask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }
    public Subtask getSubtaskId(Long id) {
        return subTasks.get(id);
    }


    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }
    public Task getTaskId(Long id) {
        return tasks.get(id);
    }
}
