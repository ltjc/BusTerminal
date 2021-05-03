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
            c.t=new Ticket("Left");
            System.out.println(c.getName() + " bought a ticket to left station from Ticket Counter "+no);
        }else if (ran==1){
            c.t=new Ticket("Middle");
            System.out.println(c.getName() + " bought a ticket to middle station from Ticket Counter "+no);
        }else {
            c.t= new Ticket("Right");
            System.out.println(c.getName() + " bought a ticket to right station from Ticket Counter "+no);
        }
        return true;
    }

}
