package oop.taskTreker.server;

import com.google.common.reflect.TypeToken;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import oop.taskTreker.task.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataManager {
    private final String MAIN = "main";
    private final String SIDE = "side";


    private final KVClient kvClient;
    private final Gson gson;
    private final Map<String, Data> mainDataKey;
    private final Map<String, Data> sideDataKey;

    public DataManager() {
        kvClient = new KVClient();
        mainDataKey = new HashMap<>();
        sideDataKey = new HashMap<>();
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        load();
    }

    private void load() {
        String mainDataStr = kvClient.load(MAIN);
        if (mainDataStr != null) {
            List<Data> mainData = gson.fromJson(mainDataStr, new TypeToken<ArrayList<Data>>() {}.getType());//десереализация
            mainData.forEach(d -> mainDataKey.put(d.getKey(),d));
        }

        String sideDataStr = kvClient.load(SIDE);
        if (mainDataStr != null) {
            List<Data> sideData = gson.fromJson(sideDataStr, new TypeToken<ArrayList<Data>>() {}.getType());
            sideData.forEach(d -> mainDataKey.put(d.getKey(),d));
        }
    }

    public Data getMainKey(String key) {
        return mainDataKey.get(key);
    }
    public Data getSideKey(String key) {
        return sideDataKey.get(key);
    }

    public void saveMain(Data data) {
        mainDataKey.put(data.getKey(),data);
        String json = new Gson().toJson(mainDataKey.values());
        kvClient.put(MAIN,json);
    }

    public void saveSide(Data data) {
        mainDataKey.put(data.getKey(),data);
        String json = new Gson().toJson(sideDataKey.values());
        kvClient.put(SIDE,json);
    }

   // private void backup() {}
}
