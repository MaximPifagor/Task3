package Wallet.Client2;

import java.net.InetAddress;
import java.net.Socket;

public class ClientApp {
    public static void main(String[] args) {
        SocketController controller = null;
        try {
            controller = new SocketController(new Socket(InetAddress.getLocalHost(), 8080));
        } catch (Exception e) {
            System.err.println(e);
        }
        if (controller == null) {
            return;
        }
        WalletBeginning beginning = new WalletBeginning(controller);
    }
}
