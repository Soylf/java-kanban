package oop.taskTreker.task;

import java.util.Objects;

public class Data {
    private final String key;
    private final String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Data(String key, String name) {
        this.key = key;
        this.name = name;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(key, data.key) && Objects.equals(name, data.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, name);
    }
}