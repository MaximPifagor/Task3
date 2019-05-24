package webSocketClient;
import Dispatcher.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws Exception {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RequestForServerHash.fileOutput)));
        writer.write("");
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("Client"));
        List<RequestForServerHash> list = new ArrayList<RequestForServerHash>();
        for (int i = 0; i <2 ; i++) {
            list.add(new RequestForServerHash("hash img.bmp"));
        }
        for (int i = 0; i <5 ; i++) {
            list.add(new RequestForServerHash("list"));
        }
        dispatcher.addAll(list);
        Thread.sleep(1000);
        dispatcher.Interrupt();
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i).resp);
            System.out.println();
        }
    }
}
