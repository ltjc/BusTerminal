package BusTerminal;

public class Ticket {
    static int ticketNo=0;
    String route;
    boolean scanned=false;
    boolean inspected=false;

    Ticket(String route)
    {
        ticketNo+=1;
        this.route = route;
        scanned=false;
        inspected=false;
    }


}
