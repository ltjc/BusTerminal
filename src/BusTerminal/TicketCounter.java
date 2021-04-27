package BusTerminal;

import java.util.Random;

public class TicketCounter {
    String staffName;
    boolean toiletBreak=false;

    TicketCounter(String staffName){
        this.staffName=staffName;
    }

    synchronized boolean sellTicket(Customer c){
        if (toiletBreak==true){
            System.out.println("Staff is going for a toilet break.");
            System.out.println(c.getName()+" buy the ticket from other option");
            return false;
        }else {
            int ran= new Random().nextInt(3);
            if (ran==0){ //left
                return true;
            }else if (ran==1){//middle
                return true;
            }else {//right
                return true;
            }

        }

    }

    static void toiletBreak(){

    }


}
