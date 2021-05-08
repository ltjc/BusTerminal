package BusTerminal;

abstract public class Staff extends Thread{
    static int count=0;
    int staffID=0;

    public Staff(){
        staffID++;
    }



}
