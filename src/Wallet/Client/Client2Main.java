package Wallet.Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client2Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Socket socket = new Socket(InetAddress.getByAddress(new byte[]{(byte) 192, (byte) 168, 0, 15}), 8080);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            new Thread(new Listen(in)).start();
            while (true) {
                String s = reader.readLine();
                out.writeUTF(s);
            }
        } catch (Exception e) {

        }
    }

    static class Listen implements Runnable {
        DataInputStream in;

        public Listen(DataInputStream in) {
            this.in = in;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("ответ(" + in.readUTF() + ")");
                } catch (Exception e) {

                }
            }
        }
    }
}
