package BusTerminal;

public class Ticket {
    static int ticketNo=0;
    String station;

    Ticket(String station)
    {
        ticketNo+=1;
        this.station=station;
    }


}
