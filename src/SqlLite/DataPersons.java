package SqlLite;

import java.sql.*;
import java.text.MessageFormat;

public class DataPersons {
    Connection connection;
    Statement statement;

    public static void main(String[] args) throws Exception {
        DataPersons persons = new DataPersons("db\\users.db");
        persons.insert(new Person("rafff", "f", 1000));
        persons.updateAccount("rafff", 100);
        System.out.println(persons.getAccount("rafff"));
    }

    public DataPersons(String filePath) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            statement = connection.createStatement();
            statement.execute("CREATE TABLE if not exists 'users' (login TEXT PRIMARY KEY, password TEXT, account INTEGER);");
        } catch (Exception e) {
            System.out.println("");
        }
    }

    public void insert(Person person) {
        try {
            String sql = MessageFormat.format("INSERT INTO users (login, password, account) VALUES (''{0}'', {1}, {2});", person.login, person.password, person.account);
            System.out.println(sql);
            statement.executeUpdate(sql);
        } catch (Exception e) {

        }
    }

    public void updateAccount(String login, int term) throws SQLException {
        int account = getAccount(login);
        System.out.println(account);
        String s = MessageFormat.format("UPDATE users SET account= ''{0}'' WHERE login = ''{1}'';", term + account, login);
        System.out.println(s);
        statement.executeUpdate(s);
    }

    public boolean contains(String login) {
        try {
            String sql = MessageFormat.format("SELECT login FROM persons where login = '{0}';", login);
            ResultSet query = statement.executeQuery(sql);
            return query.next();
        } catch (Exception e) {
            return false;
        }
    }

    public int getAccount(String login) throws SQLException {
        String sql = MessageFormat.format("SELECT account FROM users where login=''{0}'';", login);
        System.out.println(sql);
        ResultSet query = statement.executeQuery(sql);
        query.next();
        return query.getInt(1);
    }

    public static class Person {
        public Person(String login, String password, int account) {
            this.login = login;
            this.password = password;
            this.account = account;
        }

        public String login;
        public String password;
        public int account;
    }
}
