package Reflection;

import java.util.HashMap;
import java.util.Properties;

public class Serializator {
    public static  <T> byte[] serialize(T obj) {
        if (obj == null)
            return null;
        Properties p = ClassToPropertiesConverter.classToProperties(obj);
        String result = "abc";
        for (String key : p.stringPropertyNames())
        {
            result = result + "\n" + key  + "=" + p.getProperty(key);
        }
        return  result.getBytes();
    }

    public static  <T> T deSerialize(byte[] raw) {
        String str = new String(raw);
        String[] propStr = str.split("\n");
        if(!propStr[0].equals("abc"))
            return null;
        Properties properties = new Properties();
        for (int i = 1; i <propStr.length ; i++) {
            String[] el = propStr[i].split("=");
            if(el.length == 2)
            properties.setProperty(el[0],el[1]);
        }
        return (T)ClassToPropertiesConverter.classFromProperties(properties);
    }
}
