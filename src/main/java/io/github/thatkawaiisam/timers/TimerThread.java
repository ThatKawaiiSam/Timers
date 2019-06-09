package io.github.thatkawaiisam.timers;

import io.github.thatkawaiisam.timers.api.Timer;

import java.time.Duration;
import java.time.Instant;

public class TimerThread extends Thread {

    private TimerManager timerManager;

    public TimerThread(TimerManager timerManager) {
        this.timerManager = timerManager;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                doTimerLogic();
                sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doTimerLogic() {
        Instant now = Instant.now();
        if (timerManager == null
                || now == null) {
            return;
        }
        if (timerManager.getBeginTime() == null) {
            timerManager.setBeginTime(Instant.now());
        }
        timerManager.setDeltaTime(Duration.between(timerManager.getBeginTime(), now));

        if (timerManager.getDeltaTime().getSeconds() > 0) {
            timerManager.getTimers().stream()
                    .filter(timer -> !timer.isPaused())
                    .forEach(Timer::tick);

            timerManager.setBeginTime(now);
        }

        timerManager.getTimers().stream()
                .filter(timer -> !timer.isPaused())
                .filter(Timer::isComplete)
                .forEach(Timer::onComplete);
    }
}
