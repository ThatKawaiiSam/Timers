package io.github.thatkawaiisam.timers;

import io.github.thatkawaiisam.timers.api.Timer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
@RequiredArgsConstructor
public class TimerManager {

    private final Set<Timer> timers = ConcurrentHashMap.newKeySet();
    private final TimerThread thread = new TimerThread(this);

    private Duration deltaTime = Duration.ZERO;
    private Instant beginTime = Instant.now();


    /**
     * add_timer
     *
     * Adds a timer to the {@link TimerManager#timers} collection
     *
     * @param timer The timer to add
     */
    public void addTimer(Timer timer) {
        timers.add(timer);
    }

    /**
     * remove_timer
     *
     * Removes a from to the {@link TimerManager#timers} collection
     *
     * @param timer The timer to remove
     */
    public void removeTimer(Timer timer) {
        timers.remove(timer);
    }

    public void cleanup() {
        //TODO decide if I should auto end the timers?
        timers.clear();
        this.thread.stop();
    }

}
