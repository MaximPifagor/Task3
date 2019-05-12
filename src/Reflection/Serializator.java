package Reflection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Properties;

public class Serializator {
    public static <T> byte[] serialize(T obj) {
        if (obj == null)
            return null;
        Properties p = ClassToPropertiesConverter.classToProperties(obj);
        StringWriter writer = new StringWriter();
        try {
            p.store(writer, "");
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
        return writer.toString().getBytes();
    }

    public static <T> T deSerialize(byte[] raw) {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(new String(raw)));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
        return (T) ClassToPropertiesConverter.classFromProperties(properties);
    }
}
