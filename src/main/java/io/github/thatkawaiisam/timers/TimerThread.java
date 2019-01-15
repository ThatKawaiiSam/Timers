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
            doTimerLogic();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doTimerLogic() {
        Instant now = Instant.now();
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
