package webSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import Dispatcher.*;

public class MyServer extends Threaded {
    private Socket socket;

    public MyServer(String name,Socket socketClient) {
        super(name);
        socket = socketClient;
    }

    public void subRun() {
        try{
            BufferedReader reader;
            BufferedWriter writer;
            try{
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = "";
                String s ="";
                while ((s = reader.readLine()).length() != 0){
                    str +=s;
                }

            }finally {

            }
        }catch (Exception e){

        }

    }
}
