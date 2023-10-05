package oop.taskTreker.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private  ArrayList<Long> subTaskIds;
    private LocalDateTime endTime;

    public Epic(String name, String desc) {
        super(name, desc);
        subTaskIds = new ArrayList<>();
    }

    public Epic(long id, TaskType type, String name, String status, String desc, String val) {
        super();
    }

    public Epic(String epic1, String descriptionEpic1, Status aNew) {
    }


    public ArrayList<Long> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Long> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    public void addSubTasks(Long subtasksId){
        subTaskIds.add(subtasksId);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
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



