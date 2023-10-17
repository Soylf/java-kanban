package oop.taskTreker.converter;


import com.google.gson.*;
import oop.taskTreker.task.Task;


import java.lang.reflect.Type;

public class TaskSerializer implements JsonSerializer<Task> {

    @Override
    public JsonElement serialize(Task task, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", task.getName());
        jsonObject.addProperty("description", task.getDesc());
        jsonObject.addProperty("id", task.getId().toString());
        jsonObject.addProperty("status", task.getStatus().toString());
        if (task.getStartTime() != null) {
            jsonObject.addProperty("startTime", task.getStartTime().toString());
        }
        if (task.getDuration() != null) {
            jsonObject.addProperty("duration", task.getDuration().toString());
        }
        return jsonObject;
    }
}