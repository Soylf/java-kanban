package oop.taskTreker.server;


import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import oop.taskTreker.converter.*;
import oop.taskTreker.manager.fileManager.FileBackedTasksManager;
import oop.taskTreker.task.Epic;
import oop.taskTreker.task.Subtask;
import oop.taskTreker.task.Task;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HttpTaskManager extends FileBackedTasksManager {
    private static final String TASK = "task";
    private static final String EPIC = "epic";
    private static final String SUBTASK = "subtask";
    private static final String HISTORY = "history";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Task.class, new TaskSerializer())
            .registerTypeAdapter(Epic.class, new EpicSerializer())
            .registerTypeAdapter(Subtask.class, new SubtaskSerializer())
            .registerTypeAdapter(Task.class, new TaskDeserializer())
            .registerTypeAdapter(Epic.class, new EpicDeserializer())
            .registerTypeAdapter(Subtask.class, new SubtaskDeserializer())
            .serializeNulls()
            .create();

    private final KVClient kvTaskClient;


    public HttpTaskManager() throws IOException {
        super();
        kvTaskClient = new KVClient();
        restoreFromLocalHost();
    }

    private void restoreFromLocalHost() {
        try {
            String tasksJson = kvTaskClient.load(TASK);
            List<Task> tasksList = gson.fromJson(tasksJson, new TypeToken<ArrayList<Task>>() {
            }.getType());
            if (tasksList != null) {
                tasksList.forEach(t -> {
                    getTasks().add(t);
                    prioritizedTasks.add(t);
                });
            }

            String epicsJson = kvTaskClient.load(EPIC);
            List<Epic> epicsList = gson.fromJson(epicsJson, new TypeToken<ArrayList<Epic>>() {
            }.getType());
            if (epicsList != null) {
                epicsList.forEach(epic -> {
                    getEpics().add(epic);
                    prioritizedTasks.add(epic);
                });
            }

            String subtasksJson = kvTaskClient.load(SUBTASK);
            List<Subtask> subTaskList = gson.fromJson(subtasksJson, new TypeToken<ArrayList<Subtask>>() {
            }.getType());
            if (subTaskList != null) {
                subTaskList.forEach(st -> {
                    getSubTasks().add(st);
                    prioritizedTasks.add(st);
                });
            }

            String historyJson = kvTaskClient.load(HISTORY);
            List<Task> restoreHistory = gson.fromJson(historyJson, new TypeToken<ArrayList<Task>>() {
            }.getType());
            if (restoreHistory != null) {
                restoreHistory.forEach(inMemoryHistoryManager::add);
            }
            combineEpicPlusSubtask();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void save() {
        kvTaskClient.put("tasks", gson.toJson(getTasks()));
        kvTaskClient.put("subtasks", gson.toJson(getSubTasks()));
        kvTaskClient.put("epics", gson.toJson(getEpics()));
        kvTaskClient.put("history", gson.toJson(inMemoryHistoryManager.getHistory()));
    }
    private void combineEpicPlusSubtask() {
        if (!this.getEpics().isEmpty()) {
            if (!this.getSubTasks().isEmpty()) {
                for (Epic epic : getEpics()) {
                    for (Subtask subtask : getSubTasks()) {
                        if (Objects.equals(subtask.getEpicId(), epic.getId())) {
                            epic.getSubTaskIds().add(subtask.getId());
                        }
                    }
                }
            }
        }
    }
}