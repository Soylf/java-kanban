package oop.taskTreker.server;


import com.google.gson.*;
import oop.taskTreker.manager.Managers;
import oop.taskTreker.manager.fileManager.FileBackedTasksManager;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HttpTaskManager extends FileBackedTasksManager {
    private KVClient kvTaskClient;
    private Gson gson;

    public HttpTaskManager(String serverUrl) throws IOException {
        super();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .create();
        this.kvTaskClient = (KVClient) Managers.getDefault(serverUrl);
    }


    @Override
    public void save() {
        kvTaskClient.put("tasks", gson.toJson(getTasks()));
        kvTaskClient.put("subtasks", gson.toJson(getSubTasks()));
        kvTaskClient.put("epics", gson.toJson(getEpics()));
        kvTaskClient.put("history", gson.toJson(inMemoryHistoryManager.getHistory()));
    }
}