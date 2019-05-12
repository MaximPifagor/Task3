package Reflection;
import FileWorkerPackage.*;

public class Packet {
    public int number;
    public String name;
    public char ch;
    public byte aByte;
    public Packet2 packet2;

    public Packet(int number, String name, char ch, byte aByte, Packet2 packet2) {
        this.number = number;
        this.name = name;
        this.ch = ch;
        this.aByte = aByte;
        this.packet2 = packet2;
    }

    public Packet(){

    }

}
