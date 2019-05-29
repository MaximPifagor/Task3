package Wallet.WalletService;

import javafx.util.Pair;
import sun.dc.pr.PRError;
import webSocket.ServerBase;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.WriteAbortedException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.time.Period;
import java.util.*;

public class WalletServer extends ServerBase {
    static volatile DataBase persons;
    static volatile HashMap<String, WalletServer> activePersons = new HashMap<>();
    Person currentPerson;
    States currentState;

    public WalletServer(String n, Socket client) throws IOException {
        super(n, client);
        currentState = States.Start;
    }

    @Override
    public String doCommands(String command) {
        String result = subDoCommand(command);
        return result;
    }


    private String subDoCommand(String command) {
        if (command.equals("quit")) {
            return quit(command);
        }
        if (currentState == States.Start && command.startsWith("reg")) {
            return registration(command);
        }
        if (currentState == States.Start && command.startsWith("auth")) {
            return authorization(command);
        }
        if (currentState == States.Using && command.startsWith("send")) {
            return sendVV(command);
        }
        if (currentState == States.Using && command.equals("info")) {
            return "suc:info:" + currentPerson.login + ":" + currentPerson.account;
        }
        return "unsuc:" + command;
    }

    boolean isExistLogin(String login) {
        return persons.containsKey(login);
    }

    void createNewPerson(String login, String password) {
        persons.put(login, new Person(login, password, 1000));
    }


    boolean Enter(String login, String password) {
        if (persons.containsKey(login)) {
            if (persons.get(login).password.equals(password)) {
                currentPerson = persons.get(login);
                synchronized (activePersons) {
                    activePersons.put(currentPerson.login, this);
                }
                return true;
            }
        }
        return false;
    }

    //send:<recipient>:<count>
    private String sendVV(String command) {
        String[] str = command.split(":");
        if (str.length != 3)
            return "unsuc:" + command;
        if (!isExistLogin(str[1]))
            return "unsuc:" + command;
        Person rcpt = persons.get(str[1]);
        Person source = currentPerson;
        Person person1;
        Person person2;
        if (rcpt.login.compareTo(source.login) > 0) {
            person1 = rcpt;
            person2 = source;
        } else {
            person1 = source;
            person2 = rcpt;
        }
        int count = -1;
        try {
            count = Integer.parseInt(str[2]);
        } catch (Exception e) {
            System.out.println("SendBlock sum is not number");
        }
        if (source.account >= count && count > 0) {
            synchronized (person1) {
                synchronized (person2) {
                    if (count <= currentPerson.account && count > 0) {
                        currentPerson.account = currentPerson.account - count;
                        Person p = persons.get(rcpt.login);
                        p.account = p.account + count;
                        persons.updateAccount(person1);
                        persons.updateAccount(person2);
                        update(rcpt.login);
                        update(source.login);
                        return "suc:" + command;
                    }
                }
            }
        }
        return "unsuc:" + command;
    }


    //reg:<primary login>:<password>
    public String registration(String command) {
        String[] str = command.split(":");
        if (str == null)
            return "unsuc:null";
        if (str.length != 3)
            return "unsuc:" + command;
        if (!isExistLogin(str[1])) {
            synchronized (persons) {
                if (!isExistLogin(str[1])) {
                    createNewPerson(str[1], str[2]);
                    currentState = States.Start;
                    return "suc:" + command;
                }
                return "unsec:" + command;
            }
        }
        return "unsec:" + command;
    }

    //auth:<login>:<password>
    public String authorization(String command) {
        String[] str = command.split(":");
        if (str == null)
            return "unsuc:null";
        if (str.length != 3)
            return "unsuc:" + command;
        if (Enter(str[1], str[2])) {
            currentState = States.Using;
            return "suc:" + command;
        }
        return "unsuc:" + command;
    }

    //quit
    public String quit(String command) {
        currentState = States.Start;
        for (Map.Entry<String, WalletServer> a : activePersons.entrySet()) {
            System.out.println(a.getKey() + a.getValue().currentPerson);
        }
        System.out.println();
        synchronized (activePersons) {
            activePersons.remove(currentPerson.login);
            currentPerson = null;
        }
        return "suc:quit";
    }

    public void update(String login) {
        synchronized (activePersons) {
            WalletServer s = activePersons.get(login);
            if (s != null)
                new Thread(new Send(s)).start();
        }
    }

    class Send implements Runnable {
        WalletServer server;

        public Send(WalletServer walletServer) {
            server = walletServer;
        }

        @Override
        public void run() {
            server.SendCommand("update:" + server.currentPerson.account);
        }
    }
}
