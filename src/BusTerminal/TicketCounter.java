//This is the ticket counter class.
//In this simulation there will be two ticket counter namely ticket counter 1 and ticket counter 2.
//In each ticket counter there will be a staff assigned to the counter.
//Ticket counter is assumed to be an object whereas ticket counter staff is assumed to be a thread.
//In a ticket counter, a ticket counter staff can prints the ticket for the customer.
//Customer is assumed there will be no preference of location, thus, ticket counter will randomly prints ticket to the customer.

package BusTerminal;
import java.util.Random;
public class TicketCounter {
    boolean toiletBreak=false; //Toilet break boolean flag to indicates if the staff is currently on toilet break.
    int no; //Number to indicate this is counter 1 or 2.
    TicketCounterStaff staff;
    TicketCounter(int no){
        this.no=no;
    }

    public boolean printTicket(Customer c){
        int ran= new Random().nextInt(3); //Route randomiser. 0 will be shortest router, 1 will be medium route and 2 will be furthest route.
        if (ran==0){//Shortest Route
            System.out.println(staff.getName() + " Printing the ticket to shortest route.");
            c.t=new Ticket("Shortest"); //Assigning the customer's ticket to respective route.
            System.out.println(c.getName() + " bought a ticket to shortest route from Ticket Counter "+no);
        }else if (ran==1){//Medium Route
            System.out.println(staff.getName() + " Printing the ticket to medium route.");
            c.t=new Ticket("Medium");//Assigning the customer's ticket to respective route.
            System.out.println(c.getName() + " bought a ticket to medium route from Ticket Counter "+no);
        }else {//Furthest Route
            System.out.println(staff.getName() + " Printing the ticket to furthest route.");
            c.t= new Ticket("Furthest");//Assigning the customer's ticket to respective route.
            System.out.println(c.getName() + " bought a ticket to furthest route from Ticket Counter "+no);
        }
        return true; //Inform the customer thread that is has successfully purchased a ticket from the ticket machine.
    }

}
