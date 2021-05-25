//This is the waiting area class.
//Each route has a waiting area and each waiting area is limited to 10 customer.
//Once the waiting area is full, the customer will wait at the foyer.
//The foyer will be shared for all customer from different waiting area.
//To limit the number of customer thread in the waiting area, a fair semaphore will be used.
//The fair semaphore will ensure the customer that the permit will be granted for customer that acquire the permit first.
//This fairness will prevent thread starvation that reduces the throughput of the programme.
//Each waiting area has a departure gate.
package BusTerminal;
import java.util.concurrent.Semaphore;

public class WaitingArea {
    //Defining the attributes of the class
    Semaphore limWait= new Semaphore(10, true); //Creating the fair semaphore object with 10 permits
    String route;
    Foyer foyer;
    ScanningMachine scanMachine;
    Bus b;

    WaitingArea(Foyer foyer, String route, ScanningMachine scanMachine, Bus b){ //passing required objects to create waiting area instance.
        this.foyer=foyer;
        this.route = route;
        this.scanMachine=scanMachine;
        this.b=b;
    }

    //Method that enables the customer to enter the waiting area.
    //In each waiting area, only 10 customers are allowed to enter the waiting area at a time.
    public boolean enter(Customer c){
        if (limWait.availablePermits()==0){ //Check the available permit, if no permit available, the customer will move to the foyer.
            System.out.println(c.getName()+" Customer: Waiting area is full. \t\t");
            System.out.println(c.getName()+" Customer: Heading to the foyer. \t\t");
            c.enteredFoyer=foyer.enterFoyer(c);//Entering the foyer.
            return false;//Indicates that the customer failed to enter waiting area.
        }
        try {
            limWait.acquire(); //If the available permits is one or more, the permit will be acquired.
            if (c.enteredFoyer==true){//If the customer has acquired the permit and currently in foyer, the customer will leave the foyer.
                c.enteredFoyer=foyer.leaveFoyer(c); //Customer leaves the foyer.
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(c.getName()+"Customer: has enter the "+c.selectedWA.route + "route waiting area.\t\t"+limWait.availablePermits()+" left left in waiting area"+c.selectedWA.route);
        return true; //Indicates that the customer has successfully enter the waiting area.
    }

    //This method enables the customer to leave the waiting area.
    //When leaving the waiting area the acquired permit will be released.
    //Customer will leave the waiting when heading to the departure gate for ticket scanning and inspecting.
    public void leaveWA(Customer c){
        limWait.release();//Releasing the acquired permit.
        System.out.println(c.getName()+" Customer: Heading to the "+c.selectedWA.route + "route departure gate.\t\t"+limWait.availablePermits()+" left in waiting area"+c.selectedWA.route);
    }

}
