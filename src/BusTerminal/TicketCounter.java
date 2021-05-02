package BusTerminal;

import java.util.ArrayList;
import java.util.Random;

public class TicketCounter {
    boolean toiletBreak=false;
    boolean occupied=false;
    TicketCounterStaff staff= new TicketCounterStaff(this);
    ArrayList<Customer> customerAL= new ArrayList<Customer>();
    TicketCounter(){}

    TicketCounter(TicketCounterStaff staff, ArrayList<Customer> al){
        this.staff=staff;
        this.customerAL=al;
    }




}
