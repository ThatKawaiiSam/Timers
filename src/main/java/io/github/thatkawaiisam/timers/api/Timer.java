package io.github.thatkawaiisam.timers.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Getter @Setter
@RequiredArgsConstructor
public abstract class Timer {

    private final TimerType type;

    private Long startTime = System.currentTimeMillis();
    private Long endTime = System.currentTimeMillis();

    private boolean paused = false;
    private boolean active = false;

    public void start() {
        startTime = System.currentTimeMillis();
        this.onStart();
    }

    public void start(Long startTime) {
        this.startTime = startTime;
        this.onStart();
    }

    public void start(Long startTime, Long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.onStart();
    }

    public boolean isComplete() {
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

}
