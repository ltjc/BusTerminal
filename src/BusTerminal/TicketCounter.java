package BusTerminal;

import java.util.ArrayList;
import java.util.Random;

public class TicketCounter {
    boolean toiletBreak=false;
    boolean occupied=false;
    int no;
    TicketCounterStaff staff= new TicketCounterStaff(this);
    TicketCounter(int no){
        this.no=no;
    }

    TicketCounter(int no,TicketCounterStaff staff){
        this.staff=staff;
        this.no=no;
    }

    public boolean printTicket(Customer c){
        int ran= new Random().nextInt(3);
        if (ran==0){
            c.t=new Ticket("Shortest");
            System.out.println(c.getName() + " bought a ticket to shortest route from Ticket Counter "+no);
        }else if (ran==1){
            c.t=new Ticket("Medium");
            System.out.println(c.getName() + " bought a ticket to medium route from Ticket Counter "+no);
        }else {
            c.t= new Ticket("Furthest");
            System.out.println(c.getName() + " bought a ticket to furthest route from Ticket Counter "+no);
        }
        return true;
    }

}
