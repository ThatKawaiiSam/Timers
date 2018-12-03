package io.github.thatkawaiisam.timers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class TimerHandler extends BukkitRunnable {

    private Set<AbstractTimer> abstractTimers = new HashSet<>();
    private Duration deltaTime = Duration.ZERO;
    private Instant beginTime = Instant.now();

    private JavaPlugin plugin;

    public TimerHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        //TODO messages
    }

    public void addTimer(AbstractTimer abstractTimer) {
        abstractTimers.add(abstractTimer);
    }

    public void removeTimer(AbstractTimer abstractTimer) {
        abstractTimers.remove(abstractTimer);
    }

    @Override
    public void run() {
        deltaTime = Duration.between(beginTime, Instant.now());
        if (deltaTime.getSeconds() > 0) {
            for (AbstractTimer loopCurrentAbstractTimer : abstractTimers) {
                if (!loopCurrentAbstractTimer.isPaused()) {
                    loopCurrentAbstractTimer.tick();
                }
            }
            beginTime = Instant.now();
        }
        for (AbstractTimer loopCurrentAbstractTimer : abstractTimers) {
            if (!loopCurrentAbstractTimer.isPaused()) {
                if (loopCurrentAbstractTimer.isComplete()) {
                    loopCurrentAbstractTimer.onComplete();
                }
            }
        }
    }

}
