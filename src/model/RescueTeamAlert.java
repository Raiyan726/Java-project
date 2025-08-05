package model;

public class RescueTeamAlert extends BaseMessage {
    public RescueTeamAlert(String text, int urgency) {
        super(text, urgency);
    }

    @Override
    public String getType() {
        return "Rescue";
    }

    @Override
    public int calculateResponseTime() {
        return 30 / getUrgency();
    }
}
