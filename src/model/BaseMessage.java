package model;

public abstract class BaseMessage {
    private String message;
    private int urgency;

    public BaseMessage(String message, int urgency) {
        this.message = message;
        this.urgency = urgency;
    }

    public String getMessage() {
        return message;
    }

    public int getUrgency() {
        return urgency;
    }

    // Abstract methods that subclasses must implement
    public abstract String getType();
    public abstract int calculateResponseTime();
}
