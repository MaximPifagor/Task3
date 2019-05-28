package Wallet.Client2;

import java.util.Observable;

public abstract class FrameWrapper extends Observable implements Executeable {
    public void notifyManager(String command) {
        notifyObservers(command);
    }
}
