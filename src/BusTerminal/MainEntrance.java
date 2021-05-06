package BusTerminal;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainEntrance {
    String direction;
    static int count=0;
    Guard g;

    static boolean blocked=false;

    MainEntrance(String direction, Guard g){
        this.direction=direction;
        this.g=g;
    }

    synchronized public boolean enter(Customer c) throws InterruptedException {
        try{
            if (count<100&&blocked==false){
                System.out.println(g.getName() + ": Welcome to APBT!");
                count++;
                System.out.println(c.getName() + ": Customer entered the building through " + direction + " entrance.\t\t\t\t\t Count= " + count);
                Thread.sleep(new Random().nextInt(10)*10);
                //apparent when the sleep become longer
                if (count==100){
                    blocked=true;
                }
                return true;
            }
        }catch (Exception e){}
        return false;
    }

    synchronized public void leave(Customer c){
        if (count<31&&blocked==true){
            blocked=false;
        }
        count=count-1;
        //counter gets duplicated even though sync is used
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(c.getName()+": Left APBT."+"\t\t\t\t\t Count= "+count);
        if(count==0) {
            Main.clear=true;
        }
    }

}
