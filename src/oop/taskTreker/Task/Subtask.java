package oop.taskTreker.Task;

import java.util.Objects;

public class Subtask extends Task {
    private Long epicId;

    public Subtask( String name, TaskType type,String desc,Status status,Long epicId) {
        type = TaskType.SUBTASK;
        this.epicId = epicId;
        this.status = Status.NEW;
    }



    public Subtask() {

    }

    public Long getEpicId() {
        return epicId;
    }
    public void setEpicId(Long id) {
        epicId = id;
    }
    public TaskType getType () {
        return TaskType.SUBTASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(getId(), subtask.getId()) &&
                Objects.equals(getName(), subtask.getName()) &&
                Objects.equals(getDesc(), subtask.getDesc()) &&
                Objects.equals(getStatus(), subtask.getStatus()) &&
                Objects.equals(getEpicId(), subtask.getEpicId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDesc(), getStatus(), getEpicId());
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDesc() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", epicId=" + getEpicId() +
                '}';
    }

}


