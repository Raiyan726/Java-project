package model;

public class HazardAlert extends BaseMessage {

    public HazardAlert(String text, int urgency) {
        super(text, urgency);
    } 
    
    public HazardAlert(String text) {
        super(text, 3); 
    } 
    
    @Override
    public String getType() {
        return "Hazard";
    }

    @Override
    public int calculateResponseTime() {
        return 40 / getUrgency(); 
    }

  
}

