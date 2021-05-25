//This is the ticket counter staff class.
//If the clear boolean flag is not true, the staff will run continuously.
//It is assumed that the ticket counter staff will have a break every given interval.
//To implement the interval, the staff will perform a thread sleep.
//After the staff has woken up from the sleep, the staff will check again whether all customers has leave APBT.
//If the customers have not left the bus terminal, the staff will set the toilet break to true and inform the customer thread that the staff will have a toilet break.
//Once the staff has acquire the lock, the staff will go for a toilet break.
//The break is simulated by sleeping the staff thread.
//Once the staff is back from break, the customer can acquire the lock again be purchase the ticket.
package BusTerminal;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketCounterStaff extends Thread{ //Extending thread class to inherit all properties for a thread.
    TicketCounter tc;
    Customer c;
    Lock l = new ReentrantLock(true); //Fair lock will be used to ensure the customer thread will queue up while purchasing ticket in the counter.

    public TicketCounterStaff(TicketCounter counter) {
        this.tc=counter;
    }

    public void run(){
        while (!Main.clear){//While the boolean flag in main is not clear.
            try {
                Thread.sleep(30000+new Random().nextInt(15000)); //Every 30 second to 45 second the staff will have a toilet break.
                if (!Main.clear){//Recheck the main clear boolean flag as it might changed while the ticket staff thread is sleeping.
                    tc.toiletBreak=true;//Set the toilet flag to true, so that the customer thread will release the lock.
                    while (l.tryLock()==false){ //try until it get the lock
                        Thread.sleep(1000); //wait 1 second to check the ticket again
                        System.out.println(getName()+" ticket counter staff: Toilet break soon.");
                    }
                    //Acquired the lock
                    System.out.println(getName()+" ticket counter staff: Toilet break!!");
                    for(int i=0;i<9;i++){ //Shows the toilet break message every second. Toilet break will be 9 seconds.
                        System.out.println(getName()+" ticket counter staff: Toilet break..."+"...".repeat(i));
                        Thread.sleep(1000);
                    }
                    System.out.println(getName()+" ticket counter staff: Back from break!!");
                    tc.toiletBreak=false;
                    l.unlock();// Release the lock for the customer to obtain.
                }else {
                    break;//If main is clear break the while loop.
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

