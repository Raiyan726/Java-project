package model;

public class ResourceRequest extends BaseMessage {
    public ResourceRequest(String text, int urgency) {
        super(text, urgency);
    }

    @Override
    public String getType() {
        return "Resource";
    }

    @Override
    public int calculateResponseTime() {
        return 50 / getUrgency();
    }
}
