package io.github.thatkawaiisam.timers;

import io.github.thatkawaiisam.timers.api.Timer;

import java.time.Duration;
import java.time.Instant;

public class TimerThread extends Thread {

    private TimerManager timerManager;

    /**
     * Timer Thread.
     *
     * @param timerManager instance.
     */
    public TimerThread(TimerManager timerManager) {
        this.timerManager = timerManager;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                doTimerLogic();
                sleep(timerManager.getTickTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private void doTimerLogic() {
        final Instant now = Instant.now();
        if (timerManager == null || now == null) {
            return;
        }
        if (timerManager.getBeginTime() == null) {
            timerManager.setBeginTime(now);
        }
        timerManager.setDeltaTime(Duration.between(timerManager.getBeginTime(), now));

        // Check if tick should be called.
        if (timerManager.getDeltaTime().getSeconds() > 0) {
            timerManager.getTimers().stream()
                    .filter(timer -> !timer.isPaused())
                    .forEach(Timer::onSecond);
            timerManager.setBeginTime(now);
        }

        // Check if timer is complete.
        timerManager.getTimers().stream()
                .filter(timer -> !timer.isPaused())
                .filter(Timer::isComplete)
                .forEach(timer -> {
                    timer.onComplete();
                    if (timer.isRemoveOnCompletion()) {
                        timerManager.removeTimer(timer);
                    }
                });
    }
}
