package oop.taskTreker.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    protected Long id;
    protected String name;
    protected String desc;
    protected Status status;

    protected LocalDateTime startTime;

    protected Duration duration;

    public Task(String name,TaskType type ,String desc, Status status) {
        type = TaskType.TASK;
        this.name = name;
        this.desc = desc;
        this.status = Status.NEW;

    }

    public Task() {

    }

    public TaskType getType (){
        return TaskType.TASK;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(getId(), task.getId()) &&
                Objects.equals(getName(), task.getName()) &&
                Objects.equals(getDesc(), task.getDesc()) &&
                Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDesc(), getStatus());
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + getName() + '\'' +
                ", description='" + getDesc() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
