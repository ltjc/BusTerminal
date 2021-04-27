package BusTerminal;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class TicketMachine {
    Semaphore limTicket= new Semaphore(1);
    int ranTicket= new Random().nextInt(3);

    boolean generateTicket(Customer c) {
        if (limTicket.availablePermits() == 0) {
            System.out.println("Ticket machine: " + c.getName() + " please wait for the queue.");
            return false;

        } else {
            int  ran= new Random().nextInt(100);

            try {
                limTicket.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (ranTicket == 0) {
                try {
                    Thread.sleep(new Random().nextInt(100) * 3);
                }catch (Exception e){}
                breakDown(c);
                System.out.println(c.getName() + " bought a ticket to left station.");
                c.t = new Ticket("Left");
                System.out.println(c.getName() + " has left the queue");
                limTicket.release();
                return true;

            } else if (ranTicket == 1) {
                try {
                    Thread.sleep(new Random().nextInt(100) * 3);
                }catch (Exception e){}
                breakDown(c);
                System.out.println(c.getName() + " bought a ticket to middle station.");
                c.t = new Ticket("Middle");
                System.out.println(c.getName() + " has left the queue");
                limTicket.release();
                return true;

            } else {
                try {
                    Thread.sleep(new Random().nextInt(100) * 3);//step up to counter
                }catch (Exception e){}
                breakDown(c);
                System.out.println(c.getName() + " bought a ticket to right station.");
                c.t = new Ticket("Right");
                System.out.println(c.getName() + " has left the queue");
                limTicket.release();
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
                Thread.sleep(new Random().nextInt(2)*1000+new Random().nextInt(1000)); //min 2 second + 0.001 to 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}
