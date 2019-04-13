public class Main {
    public static void main(String[] args) {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("MONITOR"));
        SleepWorker[] sleepWorkers = new SleepWorker[8];
        for (int i=0;i<sleepWorkers.length;i++) {
            sleepWorkers[i] = new SleepWorker(1000* (i+1));
        }
        for (int i=0;i<sleepWorkers.length;i++) {
            dispatcher.add(sleepWorkers[i]);
        }
    }
}
