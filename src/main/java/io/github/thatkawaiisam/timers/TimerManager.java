package io.github.thatkawaiisam.timers;

import io.github.thatkawaiisam.timers.api.Timer;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class TimerManager {

    private final Set<Timer> timers = ConcurrentHashMap.newKeySet();
    private final TimerThread thread = new TimerThread(this);

    private Duration deltaTime = Duration.ZERO;
    private Instant beginTime = Instant.now();
    private int tickTime = 1;

    /**
     * Adds a timer to the {@link TimerManager#timers} collection
     *
     * @param timer The timer to add
     */
    public void addTimer(Timer timer) {
        timers.add(timer);
    }

    /**
     * Removes a from to the {@link TimerManager#timers} collection
     *
     * @param timer The timer to remove
     */
    public void removeTimer(Timer timer) {
        timers.remove(timer);
    }

    /**
     *
     */
    public void cleanup() {
        timers.clear();
        this.thread.stop();
    }

}
