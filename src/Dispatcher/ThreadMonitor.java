package Dispatcher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ThreadMonitor extends Threaded implements Observer {
    private volatile List<String> list = new ArrayList<String>();
    public final static String file = "MonitorFile.txt";
    public volatile List<String> openList = new ArrayList<>();
    private volatile boolean IsInterrupted = false;

    @Override
    public void subRun() {
        while (true && !IsInterrupted) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
            synchronized (ThreadMonitor.class) {
                if(IsInterrupted)
                    return;
                List<String> threads = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    threads.add(list.get(i));
                }
                openList = threads;
            }
        }
    }

    public void interrupt() {
        synchronized (ThreadMonitor.class) {
            IsInterrupted = true;
        }
    }


    public ThreadMonitor(String n) {
        super(n);
    }

    @Override
    public void update(Observable o, Object arg) {
        String s = (String) arg;
        String nameObj = ((Threaded) o).toString();
        String name = ((Threaded)o).name;
        if(name!= null)
            nameObj += ":" + name;
        synchronized (ThreadMonitor.class) {
            if (s.equals(Threaded.THREAD_WAS_OPENED)) {
                list.add(nameObj);
            } else if (s.equals(THREAD_WAS_CLOSED)) {
                list.remove(nameObj);
            }
        }
    }
}
