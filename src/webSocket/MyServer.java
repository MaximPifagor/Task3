package webSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import Dispatcher.*;
import FileWorkerPackage.FileWorker;
import FileWorkerPackage.Md5ExecutorFile;

public class MyServer extends Threaded {
    private Socket clientDialog;
    private static int count = 0;

    public MyServer(Socket socketClient) {
        super("SeverThread" + count);
        count++;
        clientDialog = socketClient;
    }

    public void subRun() {
        try {
            // инициируем каналы общения в сокете, для сервера

            // канал записи в сокет следует инициализировать сначала канал чтения для избежания блокировки выполнения программы на ожидании заголовка в сокете
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());

// канал чтения из сокета
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");

            System.out.println("DataOutputStream  created");
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // основная рабочая часть //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // начинаем диалог с подключенным клиентом в цикле, пока сокет не
            // закрыт клиентом
            while (!clientDialog.isClosed()) {
                System.out.println("Server reading from channel");

                // серверная нить ждёт в канале чтения (inputstream) получения
                // данных клиента после получения данных считывает их
                String entry = in.readUTF();

                // и выводит в консоль
                System.out.println("READ from clientDialog message - " + entry);

                // инициализация проверки условия продолжения работы с клиентом
                // по этому сокету по кодовому слову - quit в любом регистре
                if (entry.equalsIgnoreCase("quit")) {

                    // если кодовое слово получено то инициализируется закрытие
                    // серверной нити
                    System.out.println("Client initialize connections suicide ...");

                    out.writeUTF("Server reply - " + entry + " - OK");
                    Thread.sleep(3000);
                    break;
                }

                // если условие окончания работы не верно - продолжаем работу -
                // отправляем эхо обратно клиенту

                String result = doCommand(entry);
                System.out.println("Server try writing to channel");
                out.writeUTF("Server reply - " + '\n' +result +  '\n' + " - OK");
                System.out.println("Server Wrote message to clientDialog.");

                // освобождаем буфер сетевых сообщений
                out.flush();

                // возвращаемся в началло для считывания нового сообщения

            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // основная рабочая часть //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // если условие выхода - верно выключаем соединения
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закрываем сначала каналы сокета !
            in.close();
            out.close();

            // потом закрываем сокет общения с клиентом в нити моносервера
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String doCommand(String command){
        String result="";
        String[] s = command.split(" ");
        if (s[0].equals("hash")) {
            result = Main.fileWorkerS.getPathToHashTable().get(s[1]);
        } else if (s[0].equals("list")) {
            for (Map.Entry entry : Main.fileWorkerS.getPathToHashTable().entrySet()) {
                String respond = entry.getKey() + ": " + entry.getValue() + ";" + '\n';
                result += respond;
            }
        }
        return result;
    }
}

