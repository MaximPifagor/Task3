package Dispatcher;

public class ThreadDispatcher {
    private static volatile ThreadDispatcher threadDispatcher;
    public volatile ThreadMonitor monitor;

    public void add(Threaded task){
        task.addObserver(monitor);
        Thread thread = new Thread(task);
        thread.start();
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

    private ThreadDispatcher(ThreadMonitor monitor1) {
        monitor = monitor1;
        add(monitor);
    }
}
