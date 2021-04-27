package BusTerminal;

abstract public class Staff extends Thread{
    static int count=0;

    public Staff(){
        super(Integer.toString(count++));

    }

}
