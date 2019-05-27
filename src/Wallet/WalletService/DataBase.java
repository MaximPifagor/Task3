package Wallet.WalletService;

import FileWorkerPackage.FileWorker;
import Reflection.Serializator;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sqlite.JDBC;
import sun.awt.geom.AreaOp;

public class DataBase extends HashMap<String, Person> {
    public HashMap<String, Person> data;
    BufferedWriter writer;

    public DataBase() {
        data = new HashMap<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader("src\\Wallet\\WalletService\\Data.txt"));
        ) {
            String s = "";
            while ((s = reader.readLine()) != "") {
                String[] arr = s.split(":");
                Person p = new Person(arr[0], arr[1], Integer.parseInt(arr[2]));
                data.put(p.login, p);
            }
        } catch (Exception e) {

        }
        try {
            writer = new BufferedWriter(new FileWriter("src\\Wallet\\WalletService\\Data.txt"));
        } catch (Exception e) {

        }
    }

    @Override
    public Person get(Object key) {
        return data.get(key);
    }

    @Override
    public Person put(String key, Person value) {
        try {
            String s = value.login + ":" + value.password + ":" + value.account;
            writer.write(s);
        }catch (Exception e){

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
