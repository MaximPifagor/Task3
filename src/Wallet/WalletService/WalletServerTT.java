package Wallet.WalletService;

import SqlLite.DataPersons;
import webSocket.ClientInterfaceTT;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class WalletServerTT extends ClientInterfaceTT {
    static volatile DataPersons persons = new DataPersons("db\\users.db");
    static volatile HashMap<String, WalletServerTT> activePersons = new HashMap<>();
    DataPersons.Person currentPerson;
    States currentState;

    public WalletServerTT(String n, Socket client) throws IOException {
        super(n, client);
        currentState = States.Start;
    }

    @Override
    public String doCommand(String command) {
        String result = subDoCommand(command);
        return result;
    }

    private String subDoCommand(String command) {
//        if(command == null)
//            return "unsuc:null";
//        if (command.equals("quit")) {
//            return quit(command);
//        }
//        if (currentState == States.Start && command.startsWith("reg")) {
//            return registration(command);
//        }
//        if (currentState == States.Start && command.startsWith("auth")) {
//            return authorization(command);
//        }
//        if (currentState == States.Using && command.startsWith("send")) {
//            return sendVV(command);
//        }
//        if (currentState == States.Using && command.equals("info")) {
//            return "suc:info:" + currentPerson.login + ":" + currentPerson.account;
//        }
//        return "unsuc:" + command;
        return null;
    }

    private boolean isExistLogin(String login) {
        return persons.contains(login);
    }

    private void createNewPerson(String login, String password) {
        persons.insert(new DataPersons.Person(login, password, 1000));
    }


//    private boolean enter(String login, String password) {
//        if (isExistLogin(login)) {
//            if (persons.get(login).password.equals(password)) {
//                currentPerson = persons.get(login);
//                synchronized (activePersons) {
//                    activePersons.put(currentPerson.login, this);
//                }
//                return true;
//            }
//        }
//        return false;
//    }

    //send:<recipient>:<count>
//    private String sendVV(String command) {
//        String[] str = command.split(":");
//        if (str.length != 3)
//            return "unsuc:" + command;
//        String recipientLogin = str[1];
//        if (!isExistLogin(str[1]))
//            return "unsuc:" + command;
//        Person recipient = persons.get(recipientLogin);
//        Person person1;
//        Person person2;
//        if (recipient.login.compareTo(currentPerson.login) > 0) {
//            person1 = recipient;
//            person2 = currentPerson;
//        } else {
//            person1 = currentPerson;
//            person2 = recipient;
//        }
//        int count = -1;
//        try {
//            count = Integer.parseInt(str[2]);
//        } catch (Exception e) {
//            return "unsuc";
//        }
//        if (currentPerson.account >= count && count > 0) {
//            synchronized (person1) {
//                synchronized (person2) {
//                    if (count <= currentPerson.account && count > 0) {
//                        currentPerson.account = currentPerson.account - count;
//                        DataPersons.Person p = persons.get(recipient.login);
//                        p.account = p.account + count;
//                        persons.updateAccount(person1);
//                        persons.updateAccount(person2);
//                        update(recipient.login);
//                        update(currentPerson.login);
//                        return "suc:" + command;
//                    }
//                }
//            }
//        }
//        return "unsuc:" + command;
//    }
//
//
//    //reg:<primary login>:<password>
//    private String registration(String command) {
//        String[] str = command.split(":");
//        if (str.length != 3)
//            return "unsuc:" + command;
//        String login = str[1];
//        String password = str[2];
//        if (!isExistLogin(login)) {
//            synchronized (persons) {
//                if (!isExistLogin(login)) {
//                    createNewPerson(login, password);
//                    currentState = States.Start;
//                    return "suc:" + command;
//                }
//                return "unsec:" + command;
//            }
//        }
//        return "unsec:" + command;
//    }
//
//    //auth:<login>:<password>
//    private String authorization(String command) {
//        String[] str = command.split(":");
//        if (str.length != 3)
//            return "unsuc:" + command;
//        String login = str[1];
//        String password = str[2];
//        if (enter(login, password)) {
//            currentState = States.Using;
//            return "suc:" + command;
//        }
//        return "unsuc:" + command;
//    }
//
//    //quit
//    private String quit(String command) {
//        currentState = States.Start;
//        synchronized (activePersons) {
//            activePersons.remove(currentPerson.login);
//        }
//        return "suc:quit";
//    }
//
//    private void exit(){
//        close();
//    }
//
//    private void update(String login) {
//        synchronized (activePersons) {
//            WalletServerTT recipient = activePersons.get(login);
//            if (recipient!= null)
//                recipient.trySendRespond("updateAccount:" + recipient.currentPerson.account);
//        }
//    }
}
