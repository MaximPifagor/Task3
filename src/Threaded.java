import java.util.Observable;

public abstract class Threaded extends Observable implements Runnable {
    public String name;
    public static String THREAD_WAS_OPENED = "THREAD WAS OPENED";
    public static String THREAD_WAS_CLOSED = "THREAD WAS CLOSED";

    Threaded(String n) {
        name = n;
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
