package Reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainReflection {
    public static void main(String[] args) {
        byte[] b = fromFile();
        Packet s = Serializator.deSerialize(b);
        System.out.println(s.name + " " + s.packet2.name);
    }

    public static byte[] getBytesArray(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        Iterator<Byte> itr = list.iterator();
        int i = 0;
        while (itr.hasNext()) {
            bytes[i++] = itr.next();
        }
        return bytes;
    }

    public static void toFile(byte[] bytes) {
        FileOutputStream outStream = null;
        try {
            try {
                outStream = new FileOutputStream("src\\Reflection\\serialize.txt");
                for (int i = 0; i < bytes.length; i++) {
                    outStream.write(bytes[i]);
                }
            } finally {
                if (outStream != null)
                    outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] fromFile() {
        FileInputStream inStream = null;
        List<Byte> bytesList = new ArrayList<>();
        try {
            try {
                inStream = new FileInputStream("src\\Reflation\\serialize.txt");
                int a;
                while ((a = inStream.read()) != -1) {
                    bytesList.add((byte) a);
                }
            } finally {
                if (inStream != null)
                    inStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBytesArray(bytesList);
    }
}
