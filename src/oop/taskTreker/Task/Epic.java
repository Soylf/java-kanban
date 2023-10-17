package oop.taskTreker.task;

import oop.taskTreker.task.typeAndStatus.Status;
import oop.taskTreker.task.typeAndStatus.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private  ArrayList<Long> subTaskIds;
    private LocalDateTime endTime;

    public Epic(String name, Type type, String desc, Status status) {
        type = Type.EPIC;
        subTaskIds = new ArrayList<>();
        this.status = Status.NEW;
    }

    public Epic() {

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



