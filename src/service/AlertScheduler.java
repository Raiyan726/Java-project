package service;

import java.util.Timer;
import java.util.TimerTask;

public class AlertScheduler {

    public static void scheduleAlert(String message, int delaySeconds, Runnable action) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                action.run(); // The actual alert sending logic
                timer.cancel();
            }
        }, delaySeconds * 1000);
    }
}

