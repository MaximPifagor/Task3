package Reflection;

public class Packet2 {
    public int number;
    public String name;
    public char ch;
    public byte aByte;
    //public FileWorker fileWorker;

    public Packet2(int number, String name) {
        this.number = number;
        this.name = name;
        //fileWorker  = new FileWorker("C:\\Users\\макс\\Desktop\\test");
        ch = 'h';
        aByte = 5;
    }
    public Packet2(){

    }
}
