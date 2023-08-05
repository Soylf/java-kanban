package oop.taskTreker.Task;

import java.util.Objects;

public class Subtask extends TaskIm{
    private Long epicId;

    public Subtask( String name, String desc,Long epicId) {
        super( name, desc, "NEW");
        this.epicId = epicId;
    }

    public Long getEpicId() {
        return epicId;
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
        return "Subtask{" + '\n' +
                " name='" + getName() + '\n' +
                " description='" + getDesc() + '\n' +
                " id=" + getId() + '\n' +
                " status=" + getStatus() + '\n' +
                " parentEpicId=" + getEpicId() + '\n' +
                '}';
    }
}

