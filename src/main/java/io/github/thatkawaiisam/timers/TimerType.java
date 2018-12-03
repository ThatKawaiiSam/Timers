package io.github.thatkawaiisam.timers;

import lombok.Getter;

@Getter
public enum TimerType {

    COUNTDOWN(0),
    STOPWATCH(1);

    private int ordinal;

    TimerType(int ordinal){
        this.ordinal = ordinal;
    }

}
