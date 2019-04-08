public class ThreadMonitor extends Threaded {
    ThreadMonitor(){
        super("Monitor");
    }

    ThreadDispatcher dispatcher;

    @Override
    public void run() {

    }

    public void setDispatcher(ThreadDispatcher dispatcher1){
        dispatcher = dispatcher1;
    }
}
