package DispatcherTest;

import Dispatcher.ThreadDispatcher;
import Dispatcher.ThreadMonitor;
import com.sun.istack.internal.NotNull;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class ThreadMonitorTests {
    public final static String file = "logs\\MonitorFile.txt";

    @Test
    public void monitorTest() {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("TestMonitor"));
        SleepWorker[] sleepWorkers = new SleepWorker[10];
        ThreadMonitor monitor = dispatcher.getMonitor();
        for (int i = 0; i < sleepWorkers.length; i++) {
            sleepWorkers[i] = new SleepWorker(1000 * (i + 1));
        }
        for (int i = 0; i < sleepWorkers.length; i++) {
            dispatcher.addTask(sleepWorkers[i]);
        }
        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
            List<String> list = monitor.getActiveTasks();
            if (list.size() < 2)
                return;
            writeToFile(list);
        }
    }

    public static void writeToFile(@NotNull List<String> list) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            for (String s : list) {
                if (s != null) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFileNew(List<String> list) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)))) {
            for (String s : list) {
                writer.write(s);
                writer.newLine();
            }
        } catch (Exception e) {
             log("", e);
        }
    }

    public static void writeToConsole(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }

    private static void log(String massage, Exception e){
        System.out.println(massage);
        System.out.println(e.getStackTrace());
    }
}
