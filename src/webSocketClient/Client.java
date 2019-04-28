package webSocketClient;
import Dispatcher.*;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws Exception {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("MONITOR"));
        List<RequestForServerHash> list = new ArrayList<RequestForServerHash>();
        for (int i = 0; i <5 ; i++) {
            list.add(new RequestForServerHash("hash img.bmp"));
        }
        for (int i = 0; i <5 ; i++) {
            list.add(new RequestForServerHash("list"));
        }
        dispatcher.addAll(list);
    }
}
