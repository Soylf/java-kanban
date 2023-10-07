package oop.taskTreker.manager;

import oop.taskTreker.manager.historyManager.HistoryManager;
import oop.taskTreker.Task.Epic;
import oop.taskTreker.Task.Status;
import oop.taskTreker.Task.Subtask;
import oop.taskTreker.Task.Task;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static oop.taskTreker.manager.Managers.getDefaultHistory;

public class InMemoryTaskManager implements TaskManager {

    IdGenerator generateId = new IdGenerator();
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long, Subtask> subTasks = new HashMap<>();
    private final HashMap<Long, Task> tasks = new HashMap<>();
    protected final HistoryManager inMemoryHistoryManager = getDefaultHistory();
    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.nullsLast((o1, o2) -> {
        if (o1.getStartTime() != null && o2.getStartTime() != null) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        } else if (o1 == o2) {
            return 0;
        } else {
            return 1;
        }
    }));

    //Создание айдишника

    public long addEpicId(Epic epic) {
        epic.setId(generateId.generateId());
        epics.put(epic.getId(),epic);
        inMemoryHistoryManager.add(epic);
        return 0;
    }

    @Override
    public int addSubtaskId(Subtask subtask) {
        subtask.setId(generateId.generateId());
        subTasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getId());
        epic.addSubTasks(subtask.getId());
        updateEpicStat(subtask);
        inMemoryHistoryManager.add(epic);
        return 1;
    }
    @Override
    public int addNewTask(Task task) {
        if(task != null) {
            task.setId(generateId.generateId());
            tasks.put(task.getId(),task);
            inMemoryHistoryManager.add(task);
        }
        return 0;
    }
    @Override
    public void calculateEndTimeForEpic(Integer epicId) {
        List<LocalDateTime> test = new ArrayList<>();
        if (epics.isEmpty()) {
            return;
        }
        for (Epic epic : epics.values()) {
            if (Objects.equals(epic.getId(), epicId)) {
                List<Subtask> epicSubtasks = getEpicSubtasksByEpicId(Math.toIntExact(epic.getId()));
                if (epicSubtasks.isEmpty()) {
                    return;
                }
                for (Subtask subtask : epicSubtasks) {
                    if (subtask.getEndTime() != null) {
                        test.add(subtask.getEndTime());
                        LocalDateTime max = Collections.max(test);
                        epic.setEndTime(max);
                    }
                }
            }
        }
    }

    @Override
    public void calculateStartTimeForEpic(Integer epicId) {
        LocalDateTime startTime;
        List<LocalDateTime> test = new ArrayList<>();
        if (epics.isEmpty()) {
            return;
        }
        for (Epic epic : epics.values()) {
            if (Objects.equals(epic.getId(), epicId)) {
                List<Subtask> epicSubtasks = getEpicSubtasksByEpicId(Math.toIntExact(epic.getId()));
                if (epicSubtasks.isEmpty()) {
                    return;
                }
                for (Subtask subtask : epicSubtasks) {
                    if (subtask.getStartTime() == null) {
                        continue;
                    }
                    test.add(subtask.getStartTime());
                    LocalDateTime min = Collections.min(test);
                    epic.setStartTime(min);
                    calculateDurationTimeForEpic(Math.toIntExact(epic.getId()));
                }
            }
        }
    }
    @Override
    public void calculateDurationTimeForEpic(Integer epicId) {
        Duration duration = Duration.ZERO;
        if (epics.isEmpty()) {
            return;
        }
        for (Epic epic : epics.values()) {
            if (Objects.equals(epic.getId(), epicId)) {
                List<Subtask> epicSubtasks = getEpicSubtasksByEpicId(Math.toIntExact(epic.getId()));
                if (epicSubtasks.isEmpty()) {
                    return;
                }
                for (Subtask subtask : epicSubtasks) {
                    if (subtask.getDuration() == null) {
                        epic.setDuration(duration);
                        return;
                    }
                    duration = duration.plus(subtask.getDuration());
                    epic.setDuration(duration);
                }
            }
        }
    }

    @Override
    public List<Subtask> getEpicSubtasksByEpicId(Integer uniqueId) {
        List<Subtask> epicsSubtasks = new ArrayList<>();
        for (Subtask subtask : subTasks.values()) {
            if (Objects.equals(subtask, uniqueId)) {
                epicsSubtasks.add(subtask);
            }
        }
        return epicsSubtasks;
    }
    @Override
    public boolean isIntersection(Task task) {
        if (task == null) {
            return false;
        }
        if (task.getStartTime() == null) {
            return false;
        }
        if (prioritizedTasks.isEmpty()) {
            return true;
        }
        for (Task prioritized : getPrioritizedTasks()) {
            if (prioritized.getStartTime() == null) {
                continue;
            }
            if (task.getStartTime().isAfter(prioritized.getStartTime())
                    || task.getEndTime().isBefore(prioritized.getEndTime())) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void calculateEndTimeForEpic(Long epicId) {
        for (Epic epic : epics.values()) {
            if (Objects.equals(epic.getId(), epicId)) {
                calculateStartTimeForEpic(Math.toIntExact(epic.getId()));
                if (epic.getStartTime() == null || epic.getDuration() == null) {
                    return;
                }
                epic.setEndTime(epic.getStartTime().plus(epic.getDuration()));
            }
        }
    }


    //статусы - доп
    public void updateSubtaskIN_PROGRESS(Subtask subtask){
        Subtask subtask1 = subTasks.get(subtask.getId());
        subtask1.setStatus(String.valueOf(Status.IN_PROGRESS));
        updateEpicStat(subtask);
    }

    public void updateSubtaskDONE(Long subtask){
        Subtask subtask1 = subTasks.get(subtask);
        subtask1.setStatus(String.valueOf(Status.DONE));
        updateEpicStat(subtask1);
    }

    private void updateEpicStat(Subtask epicId){ //апдейт подзадач
        if(epicId == null) {
            Epic epic = epics.get(null);
            for (Long epicKey: epics.keySet()){
                epic = epics.get(epicKey);
                epic.setStatus("NEW");
            }
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
                    status = String.valueOf(subtask.getStatus());
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
    @Override
    public void deleteTask(Task task) {
        tasks.remove(task.getId());


    }
    @Override
    public void deleteSubtask(Subtask subtask) {
        Epic epic = getEpicId(subtask.getId());
        if (epic != null) {
            ArrayList<Long> subTaskIds = epic.getSubTaskIds();
            subTaskIds.remove(subtask.getId());
            epic.setSubTaskIds(subTaskIds);
            subTasks.remove(subtask.getId());
            updateEpicStat(subTasks.get(epic.getSubTaskIds()));
        }
    }
    @Override
    public void deleteTasks() {
        tasks.clear();
    }
    @Override
    public void deleteSubtasks() {
        subTasks.clear();
        updateEpicStat(null);
    }
    @Override
    public void deleteEpics() {
        subTasks.clear();
        epics.clear();
    }
    @Override
    public void deleteEpicId(long key,Epic epic1){
        epics.remove(key);

        for (Long num : epic1.getSubTaskIds()){
            subTasks.remove(num);
        }

    }
    @Override
    public void deleteAll(){
        epics.clear();
        tasks.clear();
        subTasks.clear();
        prioritizedTasks.clear();
    }


    //обновление значений
    @Override
    public void removeEpicById(Epic epic) {
        epic.setId(generateId.generateId());
    }
    @Override
    public void  removeSybTaskEpicId(Epic epic,Subtask subtask){
        subtask.setEpicId(epic.getId());
    }
    @Override
    public Task updateTask(Task task) {
        Task currentTask = tasks.get(task.getId());
        if (currentTask == null) {
            return null;
        }
        return tasks.put(task.getId(), task);
    }
    @Override
    public Subtask updateSubtask(Subtask subtask) {
        Subtask currentSubtask = subTasks.get(subtask.getId());
        if (currentSubtask == null) {
            return null;
        }
        updateEpicStat(subtask);
        return subTasks.put(subtask.getId(), subtask);
    }
    @Override
    public Epic updateEpic(Epic epic) {
        Epic currentEpic = epics.get(epic.getId());
        if (currentEpic == null) {
            return null;
        }
        updateEpicStat(subTasks.get(epic.getSubTaskIds()));
        return epics.put(epic.getId(), epic);
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        prioritizedTasks.addAll((Collection<? extends Task>) tasks);
        prioritizedTasks.addAll((Collection<? extends Task>) epics);
        prioritizedTasks.addAll((Collection<? extends Task>) subTasks);
        return new ArrayList<>(prioritizedTasks);
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
    public HashMap<Long, Subtask> subTasksFull() {
        return subTasks;
    }

    public HashMap<Long, Epic> epicsFull() {
        return epics;
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
    @Override
    public void removeTaskById(Long uniqueId) {
        for (Task task : tasks.values()) {
            if (Objects.equals(task.getId(), uniqueId)) {
                tasks.remove(task);
                inMemoryHistoryManager.remove(uniqueId);
                return;
            }
        }
    }

}
