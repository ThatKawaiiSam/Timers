package io.github.thatkawaiisam.timers.api;

import io.github.thatkawaiisam.timers.TimerManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Getter @Setter
@RequiredArgsConstructor
public abstract class Timer {

    private final TimerType type;
    private final TimerManager manager;

    private Long startTime = System.currentTimeMillis();
    private Long endTime = System.currentTimeMillis();

    private Long duration;

    private boolean paused = false;
    private boolean removeOnCompletion = true;

    public void start() {
        startTime = System.currentTimeMillis();
        checkCachedDuration();
        checkIfPresent();
        this.onStart();
    }

    public void start(Long startTime) {
        this.startTime = startTime;
        checkCachedDuration();
        checkIfPresent();
        this.onStart();
    }

    public void start(Long startTime, Long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        checkIfPresent();
        this.onStart();
    }

    public void extend() {
        if (duration != null) {
            this.endTime = System.currentTimeMillis() + duration;
        }
    }

    public void extend(Long time) {
        this.endTime = System.currentTimeMillis() + time;
    }

    public void stop() {
        manager.removeTimer(this);
        onCancel();
    }

    public void stop(boolean triggerCompleteLogic) {
        manager.removeTimer(this);
        if (triggerCompleteLogic) {
            onComplete();
        } else {
            onCancel();
        }
    }

    private void checkIfPresent() {
        if (!manager.getTimers().contains(this)) {
            manager.addTimer(this);
        }
    }

    private void checkCachedDuration() {
        if (type == TimerType.COUNTDOWN && duration != null) {
            this.endTime = startTime + duration;
        }
    }

    public boolean isComplete() {
        if (isPaused()) {
            return false;
        }
        return type == TimerType.STOPWATCH ?
                endTime - System.currentTimeMillis() >= 0 :
                endTime - System.currentTimeMillis() <= 0;
    }

    public long getSeconds(long time) {
        return TimeUnit.MILLISECONDS.toSeconds(time);
    }

    public long getMinutes(long time) {
        return TimeUnit.MILLISECONDS.toMinutes(time);
    }

    public long getHours(long time) {
        return TimeUnit.MILLISECONDS.toHours(time);
    }

    /**
     * get_remaining
     *
     * @return The time until end of the timer
     */
    public long getRemaining() {
        return endTime - System.currentTimeMillis();
    }

    /**
     * get_elapsed
     *
     * @return The current time elapsed
     */
    public long getElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    public abstract void tick();

    public abstract void onStart();

    public abstract void onComplete();

    public abstract void onCancel();

    public static long getRemaining(long endTime) {
        return endTime - System.currentTimeMillis();
    }

    public static long getElapsed(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

}
