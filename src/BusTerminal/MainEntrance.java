package BusTerminal;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainEntrance {
    String direction;
    static int count=0;
    Semaphore limSema=new Semaphore(100,true);//ensure the fairness limSema is the gate limit
    Semaphore entSema=new Semaphore(1,true); //customer limit where 1 customer at a time
    Semaphore leaveSema= new Semaphore(1,true);
    Guard g;

    boolean blocked=false;

    MainEntrance(String direction, Guard g){
        this.direction=direction;
        this.g=g;
    }

    public boolean enter(Customer c) throws InterruptedException {
        try{
            limSema.acquire();
            entSema.acquire();
            System.out.println(g.getName() + ": Welcome to APBT!");
            count++;
            Thread.sleep(new Random().nextInt(10)*100);
            //apparent when the sleep become longer
            System.out.println(c.getName() + ": Customer entered the building through " + direction + " entrance.\t\t\t\t\t Count= " + count);
            entSema.release();
        }catch (Exception e){}


        return false;
    }

    public void leave(Customer c){
        count=count-1;
        limSema.release();
        //counter gets duplicated even though sync is used
        System.out.println(c.getName()+": Left APBT."+"\t\t\t\t\t Count= "+count);
    }











}
