package oop.taskTreker.Task;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends TaskIm{
    private ArrayList<Long> subTaskIds;
    public Epic(String name, String desc) {
        super(name, desc, "NEW");
        subTaskIds = new ArrayList<>();
    }


    public ArrayList<Long> getSubTaskIds() {
        return subTaskIds;
    }

    public Long addSubTasks(Long subtasksId){
        subTaskIds.add(subtasksId);
        return subtasksId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(getId(), epic.getId()) &&
                Objects.equals(getName(), epic.getName()) &&
                Objects.equals(getDesc(), epic.getDesc()) &&
                Objects.equals(getStatus(), epic.getStatus());

    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDesc(), getStatus());
    }

    @Override
    public String toString() {
        return "Epic{" +
                " name='" + getName() +
                "', description='" + getDesc() +
                "', id=" + getId() +
                "', status=" + getStatus() +
                " }";
    }
}

