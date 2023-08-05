package oop.taskTreker.Task;

public class TaskIm {
    protected Long id;
    protected String name;
    protected String desc;
    protected String status;

    public TaskIm(String name, String desc, String status) {

        this.name = name;
        this.desc = desc;
        this.status = status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
