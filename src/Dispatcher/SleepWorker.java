package Dispatcher;

public class SleepWorker extends Threaded {
    static int i = 0;
    int TimeToLive;

    SleepWorker(int timeToLive) {
        super("SLEEP" + i);
        i++;
        TimeToLive = timeToLive;
    }

    @Override
    public void subRun() {
        try {
            Thread.sleep(TimeToLive);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
