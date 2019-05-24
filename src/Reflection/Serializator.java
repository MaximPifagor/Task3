package Reflection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLTransactionRollbackException;
import java.util.HashMap;
import java.util.Properties;

public class Serializator {
    public static <T> byte[] serialize(T obj) {
        if (obj == null)
            return null;
        Properties properties = ClassToPropertiesConverter.classToProperties(obj);
        String str = "";
        for (String key : properties.stringPropertyNames()) {
            str = str  + key + "=" + properties.getProperty(key) + "\n";
        }
//        StringWriter writer = new StringWriter();
//        try {
//            p.store(writer, "");
//        } catch (IOException ioE) {
//            ioE.printStackTrace();
//        }
        return str.getBytes();
    }

    public static <T> T deSerialize(byte[] raw) {
        if (raw == null)
            return null;
        Properties properties = new Properties();
        String str = new String(raw);
        String[] strArr = str.split("\n");
        for (int i = 0; i <strArr.length ; i++) {
            String[] entry = strArr[i].split("=");
            properties.setProperty(entry[0],entry[1]);
        }
        return (T) ClassToPropertiesConverter.classFromProperties(properties);
    }
}
