package webSocket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Dispatcher.*;

public class MyServer extends Threaded {
    OutputStream outputStream;
    InputStream inputStream;

    public MyServer(String name,OutputStream outputStream1,InputStream inputStream1) {
        super(name);
        outputStream = outputStream1;
        inputStream = inputStream1;
    }

    public void subRun() {
        try{
            BufferedReader reader;

            try{

            }finally {

            }
        }catch (Exception e){

        }

    }
}
