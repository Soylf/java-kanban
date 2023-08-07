package oop.taskTreker.task;

public class BaseTask {
    protected Long id;
    protected String name;
    protected String desc;
    protected String status;

    public BaseTask(String name, String desc) {

        this.name = name;
        this.desc = desc;
        status = String.valueOf(Status.NEW);

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
