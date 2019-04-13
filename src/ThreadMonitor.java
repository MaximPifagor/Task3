import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ThreadMonitor extends Threaded implements Observer {
    private List<String> list = new ArrayList<String>();

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
                list.add(((Threaded)o).name);
            } else if (s.startsWith(THREAD_WAS_CLOSED)) {
                list.remove(((Threaded)o).name);
            }
            writeTo();
        }
    }

    public void writeTo(){
        for (String s : list) {
            if (s != null)
                System.out.println(s);
        }
        System.out.println();
    }

}
