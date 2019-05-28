package Wallet.Client2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Deque;

public class SocketController implements ISocketController {
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    Deque<String> responds;

    public SocketController(Socket socket) throws IOException {
        socket = new Socket();
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        new Thread(this::run).start();
    }

    @Override
    public void post(String request) {
        try {
            out.writeUTF(request);
        } catch (Exception e) {
            System.out.print("can't to send request");
            System.err.println(e);
        }
    }

    public String getRespond(){
        if(!responds.isEmpty()){
            return responds.pop();
        }
        return null;
    }


    @Override
    public void run() {
        while (true) {
            try {
                String resp = in.readUTF();
                responds.add(resp);
            } catch (Exception e) {
                System.out.print("Error SniffingAble" + "Exception: ");
                System.err.println(e);
            }
        }
    }

}
