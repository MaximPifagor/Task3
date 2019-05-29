package webSocket;

import FileWorkerPackage.FileWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ServerVCS extends ServerBase {
    FileWorker worker;
    public ServerVCS(String n, Socket client, FileWorker worker) throws IOException {
        super(n, client);
        this.worker = worker;
    }

    @Override
    public String doCommands(String command) {
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
