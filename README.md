# Timers
### Features
* Fast and efficient.
* Supports both countdowns and timers.

### Code Example
```java
public class ExampleTimer extends Timer {

    public ExampleTimer() {
        super(TimerType.COUNTDOWN, "Example");
        setDuration(TimeUnit.SECONDS.toMillis(10));
    }

    @Override
    public void tick() {
        final int minutes = (int) getMinutes(getRemaining());
        final int seconds = (int) getSeconds(getRemaining());
        Bukkit.broadcastMessage("There is " + minutes + " minutes and " + seconds + " seconds remaining.");
    }

    @Override
    public void onStart() {
        Bukkit.broadcastMessage("The countdown has now started");
    }

    @Override
    public void onComplete() {
        Bukkit.broadcastMessage("The countdown is now complete!");
    }
    
}
```

### Contact
- MC-Market: https://www.mc-market.org/members/53967/
- Discord: ThatKawaiiSam#1337
- Twitter: ThatKawaiiSam
- Telegram: ThatKawaiiSam