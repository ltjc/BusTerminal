//This is the Ticket class.
//Each customer will have a ticket object that indicates that which direction the customer should go to.
//In addition, the ticket object will also helps to identify whether the ticket has been scanned and inspected.

package BusTerminal;
public class Ticket {
    String route; //Stores the route
    boolean scanned=false; //Boolean flag to indicate if the ticket is being scanned.
    boolean inspected=false; //Boolean flag to indicate if the ticket is being inspected.

    //Ticket object constructor.
    //Ticket scanned and inspected is false by default.
    Ticket(String route)
    {
        this.route = route;
        scanned=false;
        inspected=false;
    }


}
