package BusTerminal;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketMachine {

    Lock l = new ReentrantLock();
    int ranTicket;

    boolean generateTicket(Customer c) {
        if (!l.tryLock()) {// customer cannot obtain the lock
            return false;
        } else {
            ranTicket= new Random().nextInt(100);
            System.out.println(c.getName()+"Custoemr: My turn");
            if (ranTicket == 0) {
                try {
                    Thread.sleep(new Random().nextInt(100) * 3);
                    breakDown(c);
                    System.out.println(c.getName() + " bought a shortest route ticket from Ticket Machine.");
                    c.t = new Ticket("Shortest");
                    System.out.println(c.getName() + " has left the queue");
                }catch (Exception e){
                }
                finally {
                    l.unlock();
                }
                return true;

            } else if (ranTicket == 1) {
                try {
                    Thread.sleep(new Random().nextInt(100) * 3);
                    breakDown(c);
                    System.out.println(c.getName() + " bought a medium route ticket from Ticket Machine.");
                    c.t = new Ticket("Medium");
                    System.out.println(c.getName() + " has left the queue");
                }catch (Exception e){
                }finally {
                    l.unlock();
                }
                return true;
            } else {
                try {
                    Thread.sleep(new Random().nextInt(100) * 3);
                    breakDown(c);
                    System.out.println(c.getName() + " bought a furthest route ticket from Ticket Machine.");
                    c.t = new Ticket("Furthest");
                    System.out.println(c.getName() + " has left the queue");//step up to counter
                }catch (Exception e){
                }finally {
                    l.unlock();
                }
                return true;
            }
        }
    }

    void breakDown(Customer c){
        int ran= new Random().nextInt(100);
        if (ran>40){
            System.out.println("The ticket machine has broken down because of overhead!");
            System.out.println(c.getName()+" please wait for the machine to cool down.");
            try {
                Thread.sleep(new Random().nextInt(2)*100+new Random().nextInt(100)); //min 2 second + 0.001 to 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}
