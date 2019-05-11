package Reflection;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Serializator {
    HashMap<String, Integer> simpleTypes;

    public Serializator() {
        simpleTypes = new HashMap<>();
        simpleTypes.put("int", 1);
        simpleTypes.put("java.lang.String", 2);
        simpleTypes.put("double", 3);
        simpleTypes.put("char", 4);
        simpleTypes.put("byte", 5);
    }

    public <T> byte[] serialize(T obj) {
        List<Byte> bytes = new ArrayList<>();
//        bytes.add("packeg".getBytes());
        try {
            Class c = obj.getClass();
            Field[] fields = c.getFields();
            for (int i = 0; i < fields.length; i++) {
                String f = fields[i].getType().getName();
                if (simpleTypes.containsKey(f)) {
                    bytes.add((byte) simpleTypes.get(f).intValue());
                    bytes.add((byte)fields[i].getName().length());
                    bytes.addAll(bytesToBytesList(fields[i].getName().getBytes()));
                    bytes.addAll(getBytes(fields[i], obj));
                } else {
                    System.out.println("It's not the simple class's object: " + f);
                    //TODO: the method return array of bytes for composite object
                    bytes.add(((byte)0));
                    bytes.addAll(bytesToBytesList(serialize(fields[i].get(obj))));
                }
            }
        } catch (Exception e) {

        }
        return BytesListTo_byteArr(bytes);
    }

    public <T> T deSerialize(byte[] raw) {
        return null;
    }


    public List<Byte> getBytes(Field field, Object obj) throws Exception {
        Byte[] bytes = null;
        switch (field.getType().getName()) {
            case "int":
                bytes = getBytes((int) field.get(obj));
                break;
            case "char":
                bytes = getBytes((char) field.get(obj));
                break;
            case "java.lang.String":
                bytes = getBytes((String) field.get(obj));
                break;
            case "byte":
                bytes = getBytes((byte) field.get(obj));
                break;
        }
        List<Byte> listBytes = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            listBytes.add(bytes[i]);
        }
        return listBytes;
    }

    //TODO:
    public Byte[] getBytes(double d) {
        return null;
    }

    public Byte[] getBytes(int d) {
        BigInteger integer = new BigInteger(d + "", 10);
        byte[] bytes = integer.toByteArray();
        return bytesToBytes(addBytes(integer.toByteArray(),4));
    }

    public byte[] addBytes(byte[] bytes, int count){
        if(count<= bytes.length)
            return bytes;
        byte[] allbytes = new byte[count];
        int i=0;
        for (; i <allbytes.length - bytes.length ; i++) {
            allbytes[i] = (byte)0;
        }
        for (; i <allbytes.length ; i++) {
            allbytes[i] = bytes[i + bytes.length - allbytes.length];
        }
        return allbytes;
    }


    public Byte[] getBytes(char ch) {
        int a = (int) ch;
        BigInteger integer = new BigInteger(a + "", 10);
        byte[] bytes = integer.toByteArray();
        return bytesToBytes(addBytes(bytes,2));
    }

    public Byte[] getBytes(String str) {
        byte[] b = str.getBytes();
        byte[] f = addBytes(b,str.length()+1);
        f[0] = (byte) str.length();
        return bytesToBytes(f);
    }

    public Byte[] getBytes(byte by) {
        Byte[] bArr = new Byte[1];
        bArr[0] = by;
        return bArr;
    }

    public Byte[] bytesToBytes(byte[] b) {
        Byte[] bytes = new Byte[b.length];
        for (int i = 0; i < b.length; i++) {
            bytes[i] = b[i];
        }
        return bytes;
    }

    public List<Byte> bytesToBytesList(byte[] b) {
        ArrayList<Byte> list = new ArrayList<>();
        for (int i = 0; i < b.length; i++) {
            list.add(b[i]);
        }
        return list;
    }


    private byte[] BytesListTo_byteArr(List<Byte> bytes) {
        Iterator<Byte> itr = bytes.iterator();
        byte[] b = new byte[bytes.size()];
        int iIdx = 0;
        while (itr.hasNext()) {
            b[iIdx] = itr.next();
            iIdx++;
        }
        return b;
    }
}
