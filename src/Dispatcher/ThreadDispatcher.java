package Dispatcher;

import java.util.Collection;

public class ThreadDispatcher {
    private static volatile ThreadDispatcher threadDispatcher;
    private volatile ThreadMonitor monitor;

    private ThreadDispatcher(ThreadMonitor monitor) {
        this.monitor = monitor;
        addTask(monitor);
    }

    public ThreadMonitor getMonitor() {
        return monitor;
    }

    public void addTask(TTask task){
        task.addObserver(monitor);
        Thread thread = new Thread(task);
        thread.start();
    }

    public void addAll(Collection<? extends TTask> collection){
        for (TTask task:collection) {
            addTask(task);
        }
    }

    public static ThreadDispatcher getInstance(ThreadMonitor monitor1){
        if(threadDispatcher == null)
            synchronized (ThreadDispatcher.class){
            if(threadDispatcher==null){
                threadDispatcher = makeInstance(monitor1);
            }
            }
        return  threadDispatcher;
    }

    private static ThreadDispatcher makeInstance(ThreadMonitor monitor1){
        return new ThreadDispatcher(monitor1);
    }


}
