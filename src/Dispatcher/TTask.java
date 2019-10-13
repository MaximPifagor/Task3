package Dispatcher;

import java.util.Observable;

public abstract class TTask extends Observable implements Runnable {
    public String taskName;
    public static String THREAD_WAS_OPENED = "THREAD WAS OPENED";
    public static String THREAD_WAS_CLOSED = "THREAD WAS CLOSED";

    public TTask(String n) {
        taskName = n;
    }

    public void run() {
        setChanged();
        notifyObservers(THREAD_WAS_OPENED);
        clearChanged();
        subRun();
        setChanged();
        notifyObservers(THREAD_WAS_CLOSED);
        clearChanged();
    }

    public abstract void subRun();
}
