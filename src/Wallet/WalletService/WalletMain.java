package Wallet.WalletService;

import Dispatcher.ThreadDispatcher;
import Dispatcher.ThreadMonitor;

import java.net.ServerSocket;
import java.net.Socket;


public class WalletMain {
    public static void main(String[] args) {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("ServerMonitor"));
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                dispatcher.addTask(new WalletServerTT(client.toString(), client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
