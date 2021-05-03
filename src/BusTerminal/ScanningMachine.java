package BusTerminal;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ScanningMachine {
    Lock l= new ReentrantLock(true); //ensure fairness, prevent starvation

    public boolean scan(Customer c){
        if (!l.tryLock()){// try to get the lock, if cannot return false
            //System.out.println("Scanning machine is in use."+c.getName()+" customer, please wait");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }else {
            System.out.println("Scanning the customer "+c.getName()+"'s ticket");
            try {
                Thread.sleep(1000);//takes 1 second to scan
                System.out.println(c.getName()+" customer's ticket is scanned.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                l.unlock();
            }
            return true;
        }

    }



}
