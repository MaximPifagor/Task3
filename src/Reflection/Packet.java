package Reflection;
import FileWorkerPackage.*;

public class Packet {
    public int number;
    public String name;
    public char ch;
    public byte aByte;
    public Packet2 packet2;
    //public FileWorker fileWorker;

    public Packet(int number, String name) {
        this.number = number;
        this.name = name;
        //fileWorker  = new FileWorker("C:\\Users\\макс\\Desktop\\test");
        ch = 'h';
        aByte = 5;
        packet2 = new Packet2(3,"ooo");
    }

    public Packet(){

    }

}
