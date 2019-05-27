package Wallet.WalletService;

import javafx.util.Pair;
import sun.dc.pr.PRError;
import webSocket.ServerBase;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

public class WalletServer extends ServerBase {
    States currentState;
    static volatile DataBase persons;
    static volatile HashMap<String, Socket> activePersons = new HashMap<>();
    Person currentPerson;

    public WalletServer(String n, Socket client) {
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
            return "info:" + currentPerson.login + ":" + currentPerson.account;
        }
        return "unsec:" + command;
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
                synchronized (activePersons) {
                    currentPerson = persons.get(login);
                    WalletServer.activePersons.put(currentPerson.login, client);
                    return true;
                }
            }
        }
        return false;
    }

    //send:<recipient>:<count>
    private String sendVV(String command) {
        String[] str = command.split(":");
        if (!isExistLogin(str[1]))
            return "unsec:send";
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
        if (source.account >= Integer.parseInt(str[2])) {
            synchronized (person1) {
                synchronized (person2) {
                    int count = Integer.parseInt(str[2]);
                    if (count <= currentPerson.account) {
                        currentPerson.account = currentPerson.account - count;
                        Person p = persons.get(rcpt.login);
                        p.account = p.account + count;
                        if (activePersons.containsKey(source.login))
                            updataUsersInfo(source);
                        if (activePersons.containsKey(rcpt.login))
                            updataUsersInfo(rcpt);
                        return "sec:send";
                    }
                }
            }
        }
        return "unsec:send";
    }

    private void updataUsersInfo(Person person) {
        String login = person.login;
        synchronized (activePersons) {
            if (activePersons.containsKey(login)) {
                Socket socket = activePersons.get(login);
                try (DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
                    outputStream.writeUTF("info:" + login + ":" + person.account);
                } catch (Exception e) {
                }
            }
        }
    }

    //reg:<primary login>:<password>
    public String registration(String command) {
        String[] str = command.split(":");
        if (str == null || str.length != 3)
            return "unsuc:reg";
        if (!isExistLogin(str[1])) {
            synchronized (persons) {
                if (!isExistLogin(str[1])) {
                    createNewPerson(str[1], str[2]);
                    currentState = States.Start;
                    return "suc:reg";
                }
                return "unsec:reg";
            }
        }
        return "unsec:reg";
    }

    //auth:<login>:<password>
    public String authorization(String command) {
        String[] str = command.split(":");
        if (Enter(str[1], str[2])) {
            currentState = States.Using;
            return "suc:auth";
        }
        return "unsec:auth";
    }

    //quit
    public String quit(String command) {
        synchronized (activePersons) {
            currentState = States.Start;
            activePersons.remove(currentPerson.login);
            currentPerson = null;
            return "sec:quit";
        }
    }

}
