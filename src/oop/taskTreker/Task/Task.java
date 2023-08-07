package oop.taskTreker.task;

import java.util.Objects;

public class Task extends BaseTask {
    
    public Task(String name, String description) {
        super(name, description);
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
