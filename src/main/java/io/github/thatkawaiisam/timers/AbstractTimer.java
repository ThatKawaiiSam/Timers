package io.github.thatkawaiisam.timers;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractTimer {

    private TimerType type;
    private Long startTime = System.currentTimeMillis();
    private Long endTime;
    private boolean paused = false;

    public AbstractTimer(TimerType type) {
        this.type = type;
    }

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
        if (type == TimerType.STOPWATCH) {
            return endTime - System.currentTimeMillis() >= 0;
        }else{
            return endTime - System.currentTimeMillis() <= 0;
        }

    }

    public long remaining() {
        return Math.round(getEndTime() - System.currentTimeMillis());
    }

    public long elapsed() {
        return Math.round(System.currentTimeMillis() - getStartTime());
    }

    public abstract void tick();

    public abstract void onStart();

    public abstract void onComplete();

}
