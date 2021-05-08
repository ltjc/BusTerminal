package BusTerminal;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketInspector extends Staff{
    boolean toiletBreak=false;
    Lock l = new ReentrantLock(true);
    String currentLocation="Left";

    public void run() {
        while (!Main.clear) {
            try {
                Thread.sleep(45000 + new Random().nextInt(10000)); //45 second to 60 second
                if (!Main.clear){
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
                }else {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void  inspect(Customer c){
        if (l.tryLock() == true) { //if the customer manage to get the lock
            if (toiletBreak == false) {
                try {
                    sleep(1000);
                    if (currentLocation.equals(c.selectedWA.route)){//if ticket inspector in the correct location
                        System.out.println( this.getName() + " Ticket Inspector: Is inspecting "+c.getName()+" Customer's ticket.");
                        c.t.inspected = true;
                        System.out.println(c.getName() + " Customer: The ticket inspected.");
                    }else {
                        System.out.println(this.getName() + " Ticket Inspector: Moving to "+c.selectedWA.route +"route departure gate.");
                        currentLocation=c.selectedWA.route;
                        sleep(2000);//customer wait for 1 seconds
                        System.out.println( this.getName() + " Ticket Inspector: Is inspecting "+c.getName()+" Customer's ticket.");
                        c.t.inspected = true;
                        System.out.println(c.getName() + " Customer: The ticket inspected.");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    l.unlock();
                }
            } else {//if the staff want to go toilet release the lock
                l.unlock();
            }
        }
    }


}
