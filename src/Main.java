public class Main {
    public static void main(String[] args) {
        ThreadMonitor monitor = new ThreadMonitor();
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(monitor);
    }
}
