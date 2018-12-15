package io.github.thatkawaiisam.timers.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimerType {

    COUNTDOWN(0),
    STOPWATCH(1);

    private final int ordinal;
}
