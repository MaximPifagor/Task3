package Reflection;

public class MainReflection {
    public static void main(String[] args) {
        Packet2 packet2 = new Packet2(2,"rrr",'v',(byte) 4);
        Packet p = new Packet(2000,"fff",'c',(byte) 5, packet2);
        byte[] bytes = Serializator.serialize(p);
        Packet s = Serializator.deSerialize(bytes);
        System.out.println(s.name+" "+s.packet2.name);
    }
}
