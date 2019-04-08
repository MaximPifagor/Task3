public class SleepWorker extends Threaded {

    SleepWorker(String n) {
        super(n);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
