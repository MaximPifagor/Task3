package webSocket;

import Dispatcher.*;
import FileWorkerPackage.FileWorker;
import FileWorkerPackage.Md5ExecutorFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static int i = 0;
    static volatile FileWorker fileWorkerS;

    public static void main(String[] args) {
        FileWorker fileWorker = new FileWorker("C:\\Users\\макс\\Desktop\\test");
        fileWorker.setIsRecursive(true);
        fileWorker.execute(new Md5ExecutorFile());
        fileWorkerS = fileWorker;
        // стартуем сервер на порту 3345 и инициализируем переменную для обработки консольных команд с самого сервера
        try (ServerSocket server = new ServerSocket(3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Server socket created, command console reader for listen to server commands");

            // стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {

                // проверяем поступившие комманды из консоли сервера если такие
                // были
                if (br.ready()) {
                    System.out.println("Main Server found any messages in channel, let's look at them.");

                    // если команда - quit то инициализируем закрытие сервера и
                    // выход из цикла раздачии нитей монопоточных серверов
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Main Server initiate exiting...");
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
                System.out.print("Connection accepted.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
