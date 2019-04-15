package webSocket;
import Dispatcher.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static int i  =0;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("MONITOR"));
            while (true) {
                Socket socketClient = serverSocket.accept();
                InputStream inputStream = socketClient.getInputStream();
                OutputStream outputStream = socketClient.getOutputStream();
                MyServer myServer = new MyServer("myServer"+i++, outputStream, inputStream);
                dispatcher.add(myServer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
