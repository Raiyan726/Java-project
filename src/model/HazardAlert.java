package model;

public class HazardAlert extends BaseMessage {

    public HazardAlert(String text, int urgency) {
        super(text, urgency);
    }

    @Override
    public String getType() {
        return "Hazard";
    }

    @Override
    public int calculateResponseTime() {
        return 40 / getUrgency(); // Simple urgency formula
    }

    @Override
    public String toString() {
        return "[HazardAlert] " + super.getMessage() + " | Urgency: " + getUrgency() +
               " | Estimated Response Time: " + calculateResponseTime() + " mins";
    }
}

