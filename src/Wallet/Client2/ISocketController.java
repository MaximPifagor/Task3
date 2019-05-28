package Wallet.Client2;

public interface ISocketController extends Runnable {
    void post(String command);

    @Override
    void run();
}
