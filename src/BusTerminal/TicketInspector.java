//This is the ticket inspector class.
//A ticket inspector is assumed to be a thread class.
//It is assumed that the ticket inspector will take a break every given interval.
//Similar to ticket counter staff, once the ticket inspector is woken up from sleep, the ticket inspector will set the toilet break flag to true.
//Once the ticket inspector acquired the lock the ticket inspector will go for a break.
//After the toilet break, the customer will be able to acquire the lock.
//This implementation enables the customer to perform other tasks like scan its ticket rather waiting on the ticket inspector as the ticket inspector thread will be slept rather than the customer thread.
//In addition, this class also has a inspect method that will be called by the customer thread.
//Moreover, in this simulation the will be only one ticket inspector. Thus, the ticket inspector will move to different waiting area/departure gate to check for the customer's ticket.
//The initial location of the ticket inspector will be at the shortest route waiting area.
//Lastly, it is assumed there will be no queue, since the number of customer is controlled in the main gate and the waiting area.

package BusTerminal;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketInspector extends Thread{//Extending the thread class to inherit all properties of a thread.
    boolean toiletBreak=false; //Boolean toilet break flag to indicate if the inspector is currently on toilet break.
    Lock l = new ReentrantLock(); //Defining the lock object.
    String currentLocation="Shortest"; //This will be the initial location of the inspector.

    public void run() {
        while (!Main.clear) {//While the clear boolean flag in main class is not true
            try {
                Thread.sleep(45000 + new Random().nextInt(15000)); //45 second to 60 second before toilet break
                if (!Main.clear){//Recheck if this condition still true.
                    toiletBreak=true;//Set the toilet break to true so that the customer will release the lock.
                    while (!l.tryLock()) { //Concurrency concept- Lock, enables only one thread to obtain the lock object.Trylock()- Try until it gets the lock.
                        Thread.sleep(1000); //Wait 1 second to get the lock again.
                        System.out.println(getName() + " Ticket inspector: Toilet break soon.");
                    }
                    //At this point, the inspector has acquired the lock.
                    System.out.println(getName() + " Ticket inspector: Toilet break!!");
                    for (int i = 0; i < 9; i++) { //Wake up every interval to disply that the inspector is currently in toilet break.
                        System.out.println(getName() + " Ticket inspector: Toilet break..." + "...".repeat(i));
                        Thread.sleep(1000);
                    }
                    System.out.println(getName() + " Ticket inspector: Back from break!!");
                    toiletBreak = false;//Reset the boolean flag of toilet break to false.
                    l.unlock();// Release the lock that has been acquired.
                }else {
                    break;//If the clear flag in main thread is true, break the while loop.
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void  inspect(Customer c){ //Method for customer thread to call.
        if (l.tryLock() == true) { //Concurrency concept- Lock, enables only one thread to obtain the lock object.Trylock()- Try until it gets the lock.
            if (toiletBreak == false) {//Inspect the ticket if the inspector is not on toilet break.
                try {
                    sleep(1000);
                    //Check the location of the ticket inspector.
                    if (currentLocation.equals(c.selectedWA.route)){//If ticket inspector is in the correct location.
                        System.out.println( this.getName() + " Ticket Inspector: Is inspecting "+c.getName()+" Customer's ticket.");
                        c.t.inspected = true; //Set the inspected flag fo customer's ticket to true.
                        System.out.println(c.getName() + " Customer: The ticket inspected.");
                    }else {
                        System.out.println(this.getName() + " Ticket Inspector: Moving to "+c.selectedWA.route +"route departure gate.");
                        currentLocation=c.selectedWA.route;//Change the current location of the inspector instance.
                        sleep(2000);//Since this method is called by customer thread, the customer thread will sleep for 2 seconds to wait the ticket inspector to move to the correct departure gate.
                        System.out.println( this.getName() + " Ticket Inspector: Is inspecting "+c.getName()+" Customer's ticket.");
                        c.t.inspected = true;//Set the inspected flag fo customer's ticket to true.
                        System.out.println(c.getName() + " Customer: The ticket inspected.");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    l.unlock(); //The lock will be unlocked.
                }
            } else {//If the ticket inspector want to go toilet, the lock acquired will be released.
                l.unlock();//The lock will be unlocked.
            }
        }
    }


}
