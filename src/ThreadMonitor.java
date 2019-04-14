import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ThreadMonitor extends Threaded implements Observer {
    private List<String> list = new ArrayList<String>();
    public final static String file = "MonitorFile.txt";

    @Override
    public void subRun() {
    }

    ThreadMonitor(String n) {
        super(n);
    }

    @Override
    public void update(Observable o, Object arg) {
        String s = (String) arg;
        String[] sArr = s.split(" ");
        synchronized (ThreadMonitor.class) {
            if (s.startsWith(Threaded.THREAD_WAS_OPENED)) {
                list.add(((Threaded) o).name);
            } else if (s.startsWith(THREAD_WAS_CLOSED)) {
                list.remove(((Threaded) o).name);
            }
            writeTo();
        }
    }

    public void writeTo() {
        for (String s : list) {
            if (s != null)
                System.out.println(s);
        }
        System.out.println();
    }

    private int searchLineInFile(InputStream inputStream, String str) {
        InputStreamReader reader;
        BufferedReader bufferedReader;
        int number = -1;
        try {
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            try {
                int i = 0;
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    if (str.equals(s))
                        number = i;
                    i++;
                }
            } finally {
                reader.close();
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number == -1 ? null : number;
    }

    private void writeToFile(OutputStream outputStream, int number) {
        OutputStreamWriter writer;
        try {
            writer = new OutputStreamWriter(outputStream);
            try {

            } finally {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
