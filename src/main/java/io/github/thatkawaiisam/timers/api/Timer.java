package io.github.thatkawaiisam.timers.api;

import io.github.thatkawaiisam.timers.api.TimerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public abstract class Timer {

    private final TimerType type;

    private Long startTime = System.currentTimeMillis();
    private Long endTime;

    private boolean paused = false;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void start(Long startTime) {
        this.startTime = startTime;
    }

    public void start(Long startTime, Long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isComplete() {
        return type == TimerType.STOPWATCH ?
                endTime - System.currentTimeMillis() >= 0 :
                endTime - System.currentTimeMillis() <= 0;
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
