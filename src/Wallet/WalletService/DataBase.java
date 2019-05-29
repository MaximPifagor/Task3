package Wallet.WalletService;

import FileWorkerPackage.FileWorker;
import Reflection.Serializator;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import SqlLite.DataPersons;
import org.sqlite.JDBC;
import sun.awt.geom.AreaOp;

public class DataBase extends HashMap<String, Person> {
    public HashMap<String, Person> data;
    DataPersons persons;

    public DataBase() {
        data = new HashMap<>();
        try {
            persons = new DataPersons("src\\Wallet\\WalletService\\users.db");
            data = persons.getTable();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }

    @Override
    public Person get(Object key) {
        return data.get(key);
    }

    public void updateAccount(Person person) {
        try {
            persons.update(person);
        } catch (Exception e) {
            System.err.println("unupdateable person ");
        }
    }

    @Override
    public Person put(String key, Person value) {
        try {
            persons.Insert(value);
        } catch (Exception e) {

        }
        return data.put(key, value);
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    public static DataBase LoadFromFile(String filePath) {
        List<Byte> bytes = new ArrayList<>();
        try (
                FileInputStream stream = new FileInputStream(filePath);
        ) {
            int a;
            while ((a = stream.read()) != -1) {
                bytes.add((byte) a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytes1 = new byte[bytes.size()];
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i] = bytes.get(i);
        }
        return (DataBase) Serializator.deSerialize(bytes1);
    }

    public static void storeToFile(String filePath, DataBase base) {
        byte[] bytes = Serializator.serialize(base);
        try (
                FileOutputStream outputStream = new FileOutputStream(filePath);
        ) {
            for (int i = 0; i < bytes.length; i++) {
                outputStream.write(bytes[i]);
            }
        } catch (Exception e) {

        }
    }
}
