package webSocket;

import Dispatcher.Threaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class ServerBase extends Threaded implements IServer<String> {
    protected Socket client;

    public ServerBase(String n, Socket client) {
        super(n);
        this.client = client;
    }

    @Override
    public void subRun() {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            while (!client.isClosed()) {
                String command = in.readUTF();
                if(command.equals("exit"))
                    break;
                String respond = doCommands(command);
                out.writeUTF(respond);
                out.flush();
                Thread.sleep(10);
            }

            in.close();
            out.close();
            client.close();
        } catch (Exception ioE) {
            ioE.printStackTrace();
        }
    }
}
