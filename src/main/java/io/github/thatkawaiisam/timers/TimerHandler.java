package io.github.thatkawaiisam.timers;

import io.github.thatkawaiisam.timers.api.Timer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@RequiredArgsConstructor
public class TimerHandler extends BukkitRunnable {

    private final Set<Timer> timers = new HashSet<>();

    private Duration deltaTime = Duration.ZERO;
    private Instant beginTime = Instant.now();

    private final JavaPlugin plugin;

    /**
     * add_timer
     *
     * Adds a timer to the {@link TimerHandler#timers} collection
     *
     * @param timer The timer to add
     */
    public void addTimer(Timer timer) {
        timers.add(timer);
    }

    /**
     * remove_timer
     *
     * Removes a from to the {@link TimerHandler#timers} collection
     *
     * @param timer The timer to remove
     */
    public void removeTimer(Timer timer) {
        timers.remove(timer);
    }

    @Override
    public void run() {
        Instant now = Instant.now();

        deltaTime = Duration.between(beginTime, now);

        if (deltaTime.getSeconds() > 0) {
            timers.stream()
                    .filter(timer -> !timer.isPaused())
                    .forEach(Timer::tick);

            beginTime = now;
        }

        timers.stream()
                .filter(timer -> !timer.isPaused())
                .filter(Timer::isComplete)
                .forEach(Timer::onComplete);
    }

}
