package oop.taskTreker.converter;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import oop.taskTreker.task.Epic;


import java.lang.reflect.Type;

public class EpicSerializer implements JsonSerializer<Epic> {
    @Override
    public JsonElement serialize(Epic epic, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", epic.getName());
        jsonObject.addProperty("description", epic.getDesc());
        jsonObject.addProperty("id", epic.getId().toString());
        jsonObject.addProperty("status", epic.getStatus().toString());
        if (epic.getStartTime() != null) {
            jsonObject.addProperty("startTime", epic.getStartTime().toString());
        }
        if (epic.getDuration() != null) {
            jsonObject.addProperty("duration", epic.getDuration().toString());
        }
        if (epic.getEndTime() != null) {
            jsonObject.addProperty("endTime", epic.getEndTime().toString());
        }
        return jsonObject;
    }
}