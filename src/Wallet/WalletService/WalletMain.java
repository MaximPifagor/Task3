package Wallet.WalletService;

import Dispatcher.ThreadDispatcher;
import Dispatcher.ThreadMonitor;
import webSocket.ServerVCS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

import org.sqlite.JDBC;


public class WalletMain {
    public static void main(String[] args) {
        DataBase base = new DataBase();
        WalletServer.persons = base;
        Observable observable = new Observable();
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("ServerWallet"));
        try (ServerSocket serverSocket = new ServerSocket(8080);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                dispatcher.add(new WalletServer(client.toString(), client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBase.storeToFile("src\\Wallet\\WalletService\\Data.txt", base);
        }
    }
}
