package BusTerminal;

public class Ticket {
    static int ticketNo=0;
    String station;
    boolean scanned=false;
    boolean inspected=false;

    Ticket(String station)
    {
        ticketNo+=1;
        this.station=station;
        scanned=false;
        inspected=false;
    }


}
