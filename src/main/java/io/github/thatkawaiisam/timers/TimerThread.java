package io.github.thatkawaiisam.timers;

public class TimerThread extends Thread {

    public TimerThread() {

    }

    @Override
    public void run() {
        while(true) {
            //TODO logic
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
