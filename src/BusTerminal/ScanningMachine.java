//This is the scanning machine class.
//In each waiting area there will be a scanning machine.
//A scanning machine can only be used by one customer at a time.
//To simulate this, a lock is used.
//If the customer failed to obtain the lock, the method will return false and customer will either tries to obtain the lock again or let the ticket check by inspector.
//If the customer managed to acquire the lock, the customer's ticket can be scanned.
//Lastly, the lock will be unlocked after the customer has scanned the ticket.
//Since, the number of customer is controlled in the main gate and the waiting area, it is assumed there will be no queue.
package BusTerminal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ScanningMachine {
    Lock l= new ReentrantLock(); //Lock will be defined here.

    public boolean scan(Customer c){
        if (!l.tryLock()){//Customer will try to get the lock, if the lock has been acquired the method will return false.
            try {
                Thread.sleep(1000);//Delay is added to simulate the customer is checking for the scanning machine.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;//This boolean will indicates that the customer have not scan its ticket.
        }else { //This is where the customer managed to obtain the lock.
            System.out.println("Scanning the customer "+c.getName()+"'s ticket");
            try {
                Thread.sleep(1000);//Delay is added to simulate the scanning action.
                System.out.println(c.getName()+" customer's ticket is scanned.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                l.unlock();//The unlock statement is enclosed in the finally block to ensure the lock will always be unlock regardless of the exception to prevent deadlock.
            }
            return true;//This boolean will indicates that the customer have scan its ticket.
        }

    }



}
