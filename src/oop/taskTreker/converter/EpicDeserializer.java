package oop.taskTreker.converter;


import com.google.gson.*;
import oop.taskTreker.task.Epic;
import oop.taskTreker.task.typeAndStatus.Status;

import java.time.Duration;
import java.time.LocalDateTime;

public class EpicDeserializer implements JsonDeserializer<Epic> {
    @Override
    public Epic deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Epic epic = new Epic();
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        Long id = (long) jsonObject.get("id").getAsInt();
        Status status = Status.valueOf(jsonObject.get("status").getAsString());
        epic.setName(name);
        epic.setDesc(description);
        epic.setId(id);
        epic.setStatus(String.valueOf(status));
        if (!(jsonObject.get("startTime") == null)) {
            LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString());
            epic.setStartTime(startTime);
        }
        if (!(jsonObject.get("duration") == null)) {
            Duration duration = Duration.parse(jsonObject.get("duration").getAsString());
            epic.setDuration(duration);
        }
        if (!(jsonObject.get("endTime") == null)) {
            LocalDateTime endTime = LocalDateTime.parse(jsonObject.get("ednTime").getAsString());
            epic.setEndTime(endTime);
        }
        return epic;
    }
}
