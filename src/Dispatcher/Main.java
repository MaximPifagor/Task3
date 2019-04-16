package Dispatcher;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Main {
    public final static String file = "MonitorFile.txt";
    public static void main(String[] args) {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("MONITOR"));
        SleepWorker[] sleepWorkers = new SleepWorker[15];
        ThreadMonitor monitor = dispatcher.monitor;
        for (int i=0;i<sleepWorkers.length;i++) {
            sleepWorkers[i] = new SleepWorker(1000* (i+1));
        }
        for (int i=0;i<sleepWorkers.length;i++) {
            dispatcher.add(sleepWorkers[i]);
        }
        while (true){
            try {
                Thread.sleep(10);
            }catch (Exception e){

            }
            List<String> list  = monitor.openList;
            writeToFile(monitor.openList);
        }
    }

    public static void writeToFile(List<String> list) {
        OutputStreamWriter writer;
        BufferedWriter bufferedWriter;
        if(list == null)
            return;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file));
            bufferedWriter = new BufferedWriter(writer);
            try {
                for (String s:list) {
                    if(s!=null) {
                        bufferedWriter.write(s);
                        bufferedWriter.newLine();
                    }
                }
            } finally {
                bufferedWriter.close();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
