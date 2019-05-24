package webSocket;

import Dispatcher.ThreadDispatcher;
import Dispatcher.ThreadMonitor;
import FileWorkerPackage.FileWorker;
import FileWorkerPackage.Md5ExecutorFile;

import java.net.ServerSocket;
import java.net.Socket;

public class MainServerVCS {
    public static void main(String[] args) {
        FileWorker worker = new FileWorker("C:\\Users\\макс\\Desktop\\test");
        worker.setIsRecursive(true);
        worker.execute(new Md5ExecutorFile());
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("ServerVCS"));
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                dispatcher.add(new ServerVCS(client.toString(), client, worker));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
