package Dispatcher;

import java.util.*;

public class ThreadMonitor extends TTask implements Observer {
    private volatile List<String> activeTTasks = new ArrayList<>();
    private volatile List<String> openActiveTTasksToRead = new LinkedList<>();

    @Override
    public void subRun() {
        while (true) {
            synchronized (ThreadMonitor.class) {
                synchronized (openActiveTTasksToRead) {
                    openActiveTTasksToRead.addAll(activeTTasks);
                }
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

    public ThreadMonitor(String monitorName) {
        super(monitorName);
    }

    public List<String> getActiveTasks(){
        List<String> activeTasks = new ArrayList<>();
        synchronized (openActiveTTasksToRead){
            activeTasks.addAll(openActiveTTasksToRead);
        }
        return activeTasks;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof String))
            return;
        String command = (String) arg;
        String nameObj = o.toString() + ":" + ((TTask) o).taskName;
        if (command.equals(TTask.THREAD_WAS_OPENED)) {
            synchronized (ThreadMonitor.class) {
                activeTTasks.add(nameObj);
            }
        } else if (command.equals(THREAD_WAS_CLOSED)) {
            synchronized (ThreadMonitor.class) {
                activeTTasks.remove(nameObj);
            }
        }
    }
}
