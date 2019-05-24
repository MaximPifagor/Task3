package webSocket.OldVersionServer;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import Dispatcher.*;
import FileWorkerPackage.FileWorker;

public class MyServer extends Threaded {
    private Socket clientDialog;
    private static int count = 0;
    private FileWorker worker;

    public MyServer(Socket socketClient,FileWorker worker) {
        super("SeverThread" + count);
        count++;
        clientDialog = socketClient;
        this.worker = worker;
    }

    public void subRun() {
        try (
                BufferedWriter log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("F", true)));
        ) {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            log.write("DataInputStream created" + '\n');
            log.write("DataOutputStream  created" + '\n');

            if (!clientDialog.isClosed()) {
                log.write("ServerBase reading from channel" + '\n');
                String entry = in.readUTF();
                log.write("READ from clientDialog message - " + entry + '\n');
                String result = doCommand(entry);
                log.write("ServerBase try writing to channel" + '\n');
                out.writeUTF(result);
                log.write("ServerBase Wrote message to clientDialog." + '\n');
                out.flush();
            }
            log.write("Client disconnected" + '\n');
            log.write("Closing connections & channels." + '\n');
            in.close();
            out.close();
            clientDialog.close();

            log.write("Closing connections & channels - DONE." + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String doCommand(String command) {
        String result = "";
        String[] s = command.split(" ");
        if (s[0].equals("hash")) {
            result = s[1] + " " + worker.getPathToHashTable().get(s[1]);
        } else if (s[0].equals("list")) {
            for (Map.Entry<String, String> entry : worker.getPathToHashTable().entrySet()) {
                String respond = entry.getKey() + '\n';
                result += respond;
            }
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}

