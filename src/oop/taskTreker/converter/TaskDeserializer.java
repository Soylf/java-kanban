package oop.taskTreker.converter;

import com.google.gson.*;
import oop.taskTreker.task.Task;
import oop.taskTreker.task.typeAndStatus.Status;


import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

public class TaskDeserializer implements JsonDeserializer<Task> {

    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Task task = new Task();
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        Long id = (long) jsonObject.get("id").getAsInt();
        Status status = Status.valueOf(jsonObject.get("status").getAsString());
        task.setName(name);
        task.setDesc(description);
        task.setId(id);
        task.setStatus(String.valueOf(status));
        if (!(jsonObject.get("startTime") == null)) {
            LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString());
            task.setStartTime(startTime);
        }
        if (!(jsonObject.get("duration") == null)) {
            Duration duration = Duration.parse(jsonObject.get("duration").getAsString());
            task.setDuration(duration);
        }
        return task;
    }
}