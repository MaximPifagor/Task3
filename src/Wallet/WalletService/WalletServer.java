package Wallet.WalletService;

import sun.dc.pr.PRError;
import webSocket.ServerBase;

import java.net.Socket;
import java.util.HashMap;

public class WalletServer extends ServerBase {
    States currentState;
    HashMap<String, String> persons = new HashMap<>();
    String login;

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
        if (currentState == States.Start && command.equals("reg")) {
            currentState = States.Registration;
            return "suc:reg";
        }
        //link:<login>:<password>
        if (currentState == States.Registration && command.startsWith("link")) {
            String[] str = command.split(":");
            if (str == null || str.length != 3)
                return "unsuc:link";
            if (!isExistLogin(str[1])) {
                boolean flag = createNewPerson(str[1], str[2]);
                currentState = States.Start;
                return "suc:link";
            }
        }
        if (currentState == States.Start && command.equals("auth")) {
            currentState = States.Authorization;
            return "suc:auth";
        }
        //enter:<login>:<password>
        if (currentState == States.Authorization && command.startsWith("enter")) {
            String[] str = command.split(":");
            if (Enter(str[1], str[2])) {
                login = str[1];
                currentState = States.Using;
                return "suc:enter";
            }
            return "unsec:enter";
        }
        //quit
        if (command.equals("quit")) {
            currentState = States.Start;
            login = null;
            return "sec:quit";
        }
        return "unsec:" + command;
    }

    private boolean isExistLogin(String login) {
        return persons.containsKey(login);
    }

    private boolean createNewPerson(String login, String password) {
        if (!persons.containsKey(login)) {
            persons.put(login, password);
            return true;
        }
        return false;
    }

    private boolean Enter(String login, String password) {
        if (persons.containsKey(login)) {
            return persons.get(login).equals(password);
        }
        return false;
    }


}
