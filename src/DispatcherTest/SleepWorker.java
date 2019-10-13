package DispatcherTest;

import Dispatcher.TTask;

//This class provide a way to test ThreadMonitor
public class SleepWorker extends TTask {
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
