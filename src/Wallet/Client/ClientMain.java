package Wallet.Client;

import java.net.InetAddress;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        try {
            WalletBeginning walletBeginning = new WalletBeginning(new Socket(InetAddress.getLocalHost(), 8080));
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
