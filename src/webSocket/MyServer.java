package webSocket;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import Dispatcher.*;

public class MyServer extends Threaded {
    private Socket clientDialog;
    private static int count = 0;

    public MyServer(Socket socketClient) {
        super("SeverThread" + count);
        count++;
        clientDialog = socketClient;
    }

    public void subRun() {
        try(
                BufferedWriter log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainServer.Log,true)));
        ) {
            // инициируем каналы общения в сокете, для сервера

            // канал записи в сокет следует инициализировать сначала канал чтения для избежания блокировки выполнения программы на ожидании заголовка в сокете
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());

// канал чтения из сокета
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            log.write("DataInputStream created"+'\n');

            log.write("DataOutputStream  created"+'\n');
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // основная рабочая часть //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (!clientDialog.isClosed()) {
                // начинаем диалог с подключенным клиентом в цикле, пока сокет не
                // закрыт клиентом

                log.write("Server reading from channel"+'\n');

                // серверная нить ждёт в канале чтения (inputstream) получения
                // данных клиента после получения данных считывает их
                String entry = in.readUTF();

                // и выводит в консоль
                log.write("READ from clientDialog message - " + entry+'\n');

                // инициализация проверки условия продолжения работы с клиентом
                // по этому сокету по кодовому слову - quit в любом регистре
//                if (entry.equalsIgnoreCase("quit")) {
//
//                    // если кодовое слово получено то инициализируется закрытие
//                    // серверной нити
//                    log.write("Client initialize connections suicide ..."+'\n');
//
//                    out.writeUTF("Server reply - " + entry + " - OK");
//                    Thread.sleep(3000);
//                }


                // если условие окончания работы не верно - продолжаем работу -
                // отправляем эхо обратно клиенту

                String result = doCommand(entry);
                log.write("Server try writing to channel"+'\n');
                out.writeUTF(result);
                log.write("Server Wrote message to clientDialog."+'\n');

                // освобождаем буфер сетевых сообщений
                out.flush();

                // возвращаемся в началло для считывания нового сообщения
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // основная рабочая часть //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // если условие выхода - верно выключаем соединения
            log.write("Client disconnected"+'\n');
            log.write("Closing connections & channels."+'\n');

            // закрываем сначала каналы сокета !
            in.close();
            out.close();

            // потом закрываем сокет общения с клиентом в нити моносервера
            clientDialog.close();

            log.write("Closing connections & channels - DONE."+'\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }

    public String doCommand(String command) {
        String result = "";
        String[] s = command.split(" ");
        if (s[0].equals("hash")) {
            result = s[1] + " "+ MainServer.fileWorkerS.getPathToHashTable().get(s[1]);
        } else if (s[0].equals("list")) {
            for (Map.Entry<String, String> entry : MainServer.fileWorkerS.getPathToHashTable().entrySet()) {
                String respond = entry.getKey() + '\n';
                result += respond;
            }
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}

