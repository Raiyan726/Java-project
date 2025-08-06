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

    
    public abstract String getType();
    public abstract int calculateResponseTime(); 
     
    @Override
    public String toString() {
        return "[" + getType() + "] " + message + " | Urgency: " + urgency +
               " | Response Time: " + calculateResponseTime() + " mins";
    }
}
