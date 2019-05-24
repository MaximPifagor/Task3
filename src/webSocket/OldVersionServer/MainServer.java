package webSocket.OldVersionServer;

import Dispatcher.*;
import FileWorkerPackage.FileWorker;
import FileWorkerPackage.Md5ExecutorFile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    static int i = 0;
    static volatile FileWorker fileWorkerS;
    static String Log = "src\\webSocket\\ServerLog";

    public static void main(String[] args) throws Exception {
        BufferedWriter serverLog = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Log)));
        serverLog.write("");
        FileWorker fileWorker = new FileWorker("C:\\Users\\макс\\Desktop\\test");
        fileWorker.setIsRecursive(true);
        fileWorker.execute(new Md5ExecutorFile());
        fileWorkerS = fileWorker;
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("ServerBase"));
        try (ServerSocket server = new ServerSocket(3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Log, true)));
        ) {
            log.write("ServerBase socket created, command console reader for listen to server commands" + '\n');

            while (!server.isClosed()) {

                if (br.ready()) {
                    log.write("MainServer ServerBase found any messages in channel, let's look at them." + '\n');
                    String serverCommand = br.readLine();
                    if (serverCommand.equals("quit")) {
                        log.write("MainServer ServerBase initiate exiting..." + '\n');
                        server.close();
                        break;
                    }
                }
                Socket client = server.accept();
                dispatcher.add(new MyServer(client,fileWorker));
                log.write("Connection accepted.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
