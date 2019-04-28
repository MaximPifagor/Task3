package webSocketClient;

import Dispatcher.Threaded;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class RequestForServerHash extends Threaded {

    public String resp;
    private String command;
    static int count = 0;
    public Socket socket;
    public volatile static String fileOutput = "ClientLog";

    public RequestForServerHash(String command) {
        super("req" + count);
        count++;
        this.command = command;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            Socket socket = new Socket(inetAddress, 3345);
            this.socket = socket;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void subRun() {
        try (
                // создаём объект для записи строк в созданный скокет, для
                // чтения строк из сокета
                // в try-with-resources стиле
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                DataInputStream ois = new DataInputStream(socket.getInputStream());
                BufferedWriter log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput,true)));
        ) {
            log.write("Client oos & ois initialized"+'\n');

            // пишем сообщение автогенерируемое циклом клиента в канал
            // сокета для сервера
            oos.writeUTF(command);

            // проталкиваем сообщение из буфера сетевых сообщений в канал
            oos.flush();

            // ждём чтобы сервер успел прочесть сообщение из сокета и
            // ответить
            Thread.sleep(10);
            log.write("Client wrote & start waiting for data from server..."+'\n');

            // забираем ответ из канала сервера в сокете
            // клиента и сохраняем её в ois переменную, печатаем на
            // консоль
            log.write("reading..."+'\n');
            String in = ois.readUTF();
            resp = in;
            //WriteToFile(in);
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void WriteToFile(String in) throws IOException{
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput,true)));
        synchronized (RequestForServerHash.class) {
            br.write(in);
            br.newLine();
            br.newLine();
        }
    }

}

