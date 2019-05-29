package SqlLite;

import Wallet.WalletService.Person;
import org.omg.CORBA.PERSIST_STORE;

import java.sql.*;
import java.util.HashMap;

public class DataPersons {
    Connection connection;
    Statement statement;
    String table;

    public static void main(String[] args) throws Exception {
        DataPersons persons = new DataPersons("users.db");
        persons.getTable();
    }

    public DataPersons(String filePath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'users' (login TEXT PRIMARY KEY, password TEXT, account INTEGER);");
    }

    public void Insert(Person person) throws SQLException {
        String val = "VALUES ('" + person.login + "', '" + person.password + "', " + person.account + ");";
        System.out.println(val);
        statement.executeUpdate("INSERT INTO users (login, password, account) " + val);
    }

    public void update(Person person) throws SQLException {
        String s = "UPDATE users\n" +
                "SET account = " + person.account + "\n" +
                "WHERE login = '" + person.login + "';\n";
        System.out.println(s);
        statement.executeUpdate(s);
    }

    public HashMap<String, Person> getTable() {
        HashMap<String, Person> table = new HashMap<>();
        try {
            ResultSet resSet = statement.executeQuery("SELECT * FROM users");
            while (resSet.next()) {
                String login = resSet.getString("login");
                String password = resSet.getString("password");
                int account = resSet.getInt("account");
                System.out.println(login + " " + password + " " + account);
                table.put(login, new Person(login, password, account));
            }
        } catch (Exception e) {
            System.err.println("ошибка при считывнии таблицы");
        }
        return table;
    }

    public boolean Contains(String login) throws SQLException {
        String s = "SELECT login FROM persons;";
        ResultSet query = statement.executeQuery(s);
        String resp = "";
        while (query.next()) {
            resp = query.getString(1);
        }
        System.out.println(resp);
        return resp.equals(login);
    }

    public void UpataAccount(Person p, int count) {

    }
}
