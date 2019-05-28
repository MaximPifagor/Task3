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
            Socket socket = new Socket(InetAddress.getLocalHost(), 8080);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            while (true) {
                String s = reader.readLine();
                out.writeUTF(s);
                Thread.sleep(10);
                System.out.println(in.readUTF());
            }
        } catch (Exception e) {

        }
    }
}
