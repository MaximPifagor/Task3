package Wallet.Client2;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

//Связывает Графический интерфейс с кслассом реализующим ISocketController
public class Manager implements Runnable, Observer {
    SocketController controller;
    FrameWrapper frame;

    public Manager(SocketController controller, FrameWrapper frame) {
        this.controller = controller;
        frame.addObserver(this::update);
    }

    @Override
    public void run() {
        while (true) {
            String s = controller.getRespond();
            if (s != null)
                frame.execute(s);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        String command = (String) arg;
    }
}
