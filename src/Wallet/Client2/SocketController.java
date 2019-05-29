package Wallet.Client2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

public class SocketController implements ISocketController {
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    volatile Deque<String> responds;

    public SocketController(Socket socket) throws IOException {
        this.socket = new Socket();
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        responds = new ArrayDeque<>();
        new Thread(this::run).start();
    }

    @Override
    public void post(String request) {
        try {
            out.writeUTF(request);
            out.flush();
        } catch (Exception e) {
            System.out.print("can't to send request");
            System.err.println(e);
        }
    }

    public String getRespond() {
        if (!responds.isEmpty()) {
            String s = responds.pop();
            return s;
        }
        return null;
    }


    @Override
    public void run() {
        while (true) {
            try {
                String resp = in.readUTF();
                if (resp != null) {
                    responds.push(resp);
                    System.out.println(resp);
                }
            } catch (Exception e) {
                System.out.print("Error SniffingAble" + "Exception: ");
                System.err.println(e);
            }
        }
    }

}
