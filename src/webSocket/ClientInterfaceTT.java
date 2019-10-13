package webSocket;

import Dispatcher.TTask;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class ClientInterfaceTT extends TTask implements IServer<String> {
    private Socket client;

    public ClientInterfaceTT(String n, Socket client) throws IOException {
        super(client.toString());
        this.client = client;
    }

    public boolean trySendRespond(String command) {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(command);
            out.flush();
            return true;
        } catch (Exception e) {
            System.out.println("can't send command to client");
            return false;
        }
    }

    @Override
    public void subRun() {
        try {
            while (!client.isClosed()) {
                DataInputStream in = new DataInputStream(client.getInputStream());
                String respond = doCommand(in.readUTF());
                if(!trySendRespond(respond))
                    break;
                Thread.sleep(10);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            close();
        }
    }

    protected void close() {
        try {
            client.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
