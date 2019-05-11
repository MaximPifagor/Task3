package webSocket;

import Dispatcher.*;
import FileWorkerPackage.FileWorker;
import FileWorkerPackage.Md5ExecutorFile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    static int i = 0;
    static volatile FileWorker fileWorkerS;
    static String Log = "ServerLog";

    public static void main(String[] args) throws Exception {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Log)));
        writer.write("");
        FileWorker fileWorker = new FileWorker("C:\\Users\\макс\\Desktop\\test");
        fileWorker.setIsRecursive(true);
        fileWorker.execute(new Md5ExecutorFile());
        fileWorkerS = fileWorker;
        // стартуем сервер на порту 3345 и инициализируем переменную для обработки консольных команд с самого сервера
        try (ServerSocket server = new ServerSocket(3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Log,true)));
        ) {
            log.write("Server socket created, command console reader for listen to server commands"+'\n');

            // стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {

                // проверяем поступившие комманды из консоли сервера если такие
                // были
                if (br.ready()) {
                    log.write("MainServer Server found any messages in channel, let's look at them."+'\n');

                    // если команда - quit то инициализируем закрытие сервера и
                    // выход из цикла раздачии нитей монопоточных серверов
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        log.write("MainServer Server initiate exiting..." + '\n');
                        server.close();
                        break;
                    }
                }

                // если комманд от сервера нет то становимся в ожидание
                // подключения к сокету общения под именем - "clientDialog" на
                // серверной стороне
                Socket client = server.accept();

                // после получения запроса на подключение сервер создаёт сокет
                // для общения с клиентом и отправляет его в отдельную нить
                // в Runnable(при необходимости можно создать Callable)
                // монопоточную нить = сервер - MonoThreadClientHandler и тот
                // продолжает общение от лица сервера
                ThreadDispatcher dispatcher = ThreadDispatcher.getInstance(new ThreadMonitor("MONITOR"));
                dispatcher.add(new MyServer(client));
                log.write("Connection accepted.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
