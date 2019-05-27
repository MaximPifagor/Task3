package Wallet.Client2;

import Dispatcher.Threaded;
import Wallet.WalletService.States;

import java.net.Socket;
import java.util.Observer;

public class ApplicationClient implements Runnable {
    Socket socket;
    Boolean isRunning;
    States currentState;

    public ApplicationClient(Socket socket) {
        this.socket = socket;
        isRunning = true;
        currentState = States.Start;
    }

    @Override
    public void run() {
        while (isRunning) {

        }
    }

    public void sendCommand() {
        
    }
}
