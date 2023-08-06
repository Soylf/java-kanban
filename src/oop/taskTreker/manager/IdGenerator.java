package oop.taskTreker.manager;

public class IdGenerator {
    private long currentValue;

    public IdGenerator() {
        this.currentValue = 0;
    }

    public long generateId() {
        return currentValue++;
    }
}
