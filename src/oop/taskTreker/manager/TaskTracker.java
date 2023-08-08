package oop.taskTreker.manager;

import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Status;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    private void updateEpicStat(Long epicId){
        if(epicId == null) {
            Epic epic = epics.get(null);
            for (Long epicKey: epics.keySet()){
                epic = epics.get(epicKey);
                epic.setStatus("NEW");
            }
            return;
        }else {
            Epic epic = epics.get(epicId);
            ArrayList<Long> subs = epic.getSubTaskIds();
            if (subs.isEmpty()) {
                epic.setStatus("NEW");
                return;
            }
            String status = null;
            for (Long id : subs) {
                final Subtask subtask = subTasks.get(id);
                if (status == null) {
                    status = subtask.getStatus();
                    continue;
                }
                if (status.equals(subtask.getStatus())
                        && !status.equals("IN_PROGRESS")) {
                    continue;
                }
                epic.setStatus("IN_PROGRESS");
                return;
            }
            epic.setStatus(status);
        }
    }


    //удаление задач
    public void deleteTask(Task task) {
        tasks.remove(task.getId());


    }

    public void deleteSubtask(Subtask subtask,Epic epic) {
        ArrayList<Long> newEpicSubId = new ArrayList<>();
        subTasks.remove(subtask.getId());
        updateEpicStat(subtask.getEpicId());
        for (Long num: epic.getSubTaskIds()) {
            if(Objects.equals(num, subtask.getId())) {
                num = null;
            }
            newEpicSubId.add(num);
        }
        epic.setSubTaskIds(newEpicSubId);
    }

    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteSubtasks() {
        subTasks.clear();
        updateEpicStat(null);
    }

    public void deleteEpics() {
        subTasks.clear();
        epics.clear();
    }

    public void deleteEpicId(long key,Epic epic1){
        epics.remove(key);

        for (Long num : epic1.getSubTaskIds()){
            subTasks.remove(num);
        }

    }

    public void deleteAll(){
        epics.clear();
        tasks.clear();
        subTasks.clear();

    }


    //обновление значений
    public void removeEpicById(Epic epic) {
        epic.setId(generateId.generateId());
    }

    public void  removeSybTaskEpicId(Epic epic,Subtask subtask){
        subtask.setEpicId(epic.getId());
    }

    public Task updateTask(Task task) {
        Task currentTask = tasks.get(task.getId());
        if (currentTask == null) {
            return null;
        }
        return tasks.put(task.getId(), task);
    }

    public Subtask updateSubtask(Subtask subtask) {
        Subtask currentSubtask = subTasks.get(subtask.getId());
        if (currentSubtask == null) {
            return null;
        }
        updateEpicStat(subtask.getEpicId());
        return subTasks.put(subtask.getId(), subtask);
    }

    public Epic updateEpic(Epic epic) {
        Epic currentEpic = epics.get(epic.getId());
        if (currentEpic == null) {
            return null;
        }
        updateEpicStat(epic.getId());
        return epics.put(epic.getId(), epic);
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

    public ArrayList<Subtask> getEpicSubtasks(Long epicId) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics.values()) {
            if (Objects.equals(epic.getId(), epicId)) {
                for (Long num: epic.getSubTaskIds()) {
                    subtasks.add(getSubtaskId(num));
                }
                break;
            }
        }
        return subtasks;
    }
}
