package BusTerminal;

import java.util.ArrayList;
import java.util.Random;

public class TicketCounter {
    boolean toiletBreak=false;
    boolean occupied=false;
    TicketCounterStaff staff= new TicketCounterStaff(this);

    TicketCounter(){}

    TicketCounter(TicketCounterStaff staff){
        this.staff=staff;

    }

    public boolean printTicket(Customer c){
        int ran= new Random().nextInt(3);
        if (ran==0){
            c.t=new Ticket("Left");
            System.out.println(c.getName() + " bought a ticket to left station.");
        }else if (ran==1){
            c.t=new Ticket("Middle");
            System.out.println(c.getName() + " bought a ticket to middle station.");
        }else {
            c.t= new Ticket("Right");
            System.out.println(c.getName() + " bought a ticket to right station.");
        }
        return true;

    }



}
