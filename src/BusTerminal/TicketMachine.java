//This is the ticket machine class.
//Ticket machine will be the shared object among all customers.
//In this simulation the will only be one ticket machine.
//Only one customer can use the ticket machine at any time.
//This machine is has 60% to break down and the reason of the problem is because of overheating.
//Since the overheat will only occur in the middle of printing the ticket, customer is assumed to wait for the ticket to cooldown.
//Ticket machine is assumed only break down wile the customer is using the machine.
//Customer is assumed to have no destination preference, thus, the machine will randomise the destination of the customer.
package BusTerminal;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketMachine{
    Lock l = new ReentrantLock(); //Creating lock object to lock the ticket machine so that only one customer can use the ticket machine at any given time.
    int ranTicket;
    boolean generateTicket(Customer c) {
        if (!l.tryLock()) {// If customer cannot obtain the lock, the customer will leave and go for other ticket purchasing options. If they manage to obtain the lock the customer can purchase the ticket.
            return false;
        } else {
            ranTicket= new Random().nextInt(3);//Randomly assign customer to different route. 0 will go for shortest router, 1 will go for medium router and 2 will go for longest route.
            System.out.println(c.getName()+"Customer: My turn");
            if (ranTicket == 0) {//Shortest Route
                try {
                    Thread.sleep(new Random().nextInt(100) * 3);
                    breakDown(c); //Breakdown event only occurs while the customer is using the ticket machine.
                    System.out.println(c.getName() + " bought a shortest route ticket from Ticket Machine.");
                    c.t = new Ticket("Shortest"); //Setting the customer's ticket to the shortest route.
                    System.out.println(c.getName() + " has left the queue");
                }catch (Exception e){
                }
                finally {//Unlock is placed in the finally block to ensure the lock will always be unlock regardless of any execption event.
                    l.unlock();
                }
                return true; //Return true to the customer once they have successfully purchased the ticket.

            } else if (ranTicket == 1) {//Medium Route
                try {//Implementation is same as shortest route.
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
            } else {//Longest Route
                try {//Implementation is same as shortest route.
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

    void breakDown(Customer c){//Breakdown  event method.
        int ran= new Random().nextInt(100);//Generating the probability.
        if (ran>38){//If the random number is greater than 38/ 39 onwards. This is because the random generator will exclude the bound and number generated will be 0 -99. (Inclusive 0)
            System.out.println("The ticket machine has broken down because of overhead!");
            System.out.println(c.getName()+" please wait for the machine to cool down.");
            try {
                Thread.sleep(new Random().nextInt(2)*100+new Random().nextInt(100)); //Delay for the cooldown
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}
