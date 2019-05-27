package SqlLite;

import Wallet.WalletService.Person;
import org.omg.CORBA.PERSIST_STORE;

import java.sql.*;

public class DataPersons {
    Connection connection;
    Statement statement;
    String table;

    public static void main(String[] args) throws Exception {
        DataPersons persons = new DataPersons("persons.db");
        persons.Insert(new Person("max","123",123));
        System.out.println(persons.Contains("max"));
    }

    public DataPersons(String filePath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'persons' ('login' VARCHAR PRIMARY KEY, 'password' VARCHAR, 'account' INT);");
    }

    public void Insert(Person person) throws SQLException {
        String val = "VALUES ('"+ person.login + "', '"  + person.password + "', '" + person.account +"');";
        System.out.println(val);
        statement.executeUpdate("INSERT INTO persons (login, password, account) " + val);
    }

    public boolean Contains(String login) throws SQLException{
        String s = "SELECT login FROM persons;";
        ResultSet query =  statement.executeQuery(s);
        String resp = "";
        while (query.next()){
            resp = query.getString(1);
        }
        System.out.println(resp);
        return resp.equals(login);
    }

    public void UpataAccount(Person p,int count){

    }
}
