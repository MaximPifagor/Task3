package webSocket;

import Dispatcher.Threaded;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class ServerBase extends Threaded implements IServer<String> {
    protected Socket client;
    protected DataOutputStream out;
    protected DataInputStream in;

    public ServerBase(String n, Socket client) throws IOException {
        super(n);
        this.client = client;
        out = new DataOutputStream(client.getOutputStream());
        in = new DataInputStream(client.getInputStream());
    }

    public void SendCommand(String command) {
        synchronized (out) {
            try {
                out.writeUTF(command);
                out.flush();
            } catch (Exception e) {
                System.out.println("cant send command to client");
            }
        }
    }

    @Override
    public void subRun() {
        try {
            try {
                while (!client.isClosed()) {
                    String command = null;
                    synchronized (in) {
                        command = in.readUTF();
                    }
                    if (command.equals("exit")) {
                        doCommands("quit");
                        break;
                    }
                    String respond = doCommands(command);
                    SendCommand(respond);
                    Thread.sleep(10);
                }
            } finally {
                in.close();
                out.close();
                client.close();
            }
        } catch (Exception ioE) {
            System.err.println(ioE);
        }
    }
}
