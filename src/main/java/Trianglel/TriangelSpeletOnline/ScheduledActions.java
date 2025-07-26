package Trianglel.TriangelSpeletOnline;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledActions {

    @Scheduled(fixedRate = 5000)
    public void scheduledActions() {

        GameHandler.gameChecker();
    }
}
