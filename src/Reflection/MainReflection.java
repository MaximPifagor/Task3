package Reflection;

public class MainReflection {
    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        byte[] bytes =  serializator.serialize(new Packet(10000,"fff"));
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(Byte.toUnsignedInt(bytes[i]));
        }
    }
}
