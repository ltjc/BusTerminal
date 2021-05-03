package BusTerminal;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketInspector extends Staff{
    boolean toiletBreak=false;
    Lock l = new ReentrantLock(true);

    public void run() {
        while (true) {
            try {
                Thread.sleep(45000 + new Random().nextInt(10000)); //45 second to 60 second

                toiletBreak=true;
                while (!l.tryLock()) { //try until it get the lock
                    Thread.sleep(1000); //wait 1 second to get the lock again
                    System.out.println(getName() + " Ticket inspector: Toilet break soon."); //try to get lock first
                }
                //acquired the lock
                System.out.println(getName() + " Ticket inspector: Toilet break!!");
                for (int i = 0; i < 9; i++) {
                    System.out.println(getName() + " Ticket inspector: Toilet break..." + "...".repeat(i));
                    Thread.sleep(1000);// break 5 to 10 second
                }
                System.out.println(getName() + " Ticket inspector: Back from break!!");
                toiletBreak = false;
                l.unlock();// release the lock
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    }
