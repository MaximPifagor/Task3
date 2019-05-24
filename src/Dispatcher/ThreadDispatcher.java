package Dispatcher;

import java.util.Collection;
import java.util.Iterator;

public class ThreadDispatcher {
    private static volatile ThreadDispatcher threadDispatcher;
    private volatile ThreadMonitor monitor;

    public ThreadMonitor getMonitor() {
        return monitor;
    }

    public void add(Threaded task){
        task.addObserver(monitor);
        Thread thread = new Thread(task);
        thread.start();
    }

    public void Interrupt(){
        monitor.interrupt();
    }

    public void addAll(Collection<? extends Threaded> collection){
        Iterator<? extends  Threaded> iterator = collection.iterator();
        while (iterator.hasNext()){
            add(iterator.next());
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

    private ThreadDispatcher(ThreadMonitor monitor1) {
        monitor = monitor1;
        add(monitor);
    }
}
