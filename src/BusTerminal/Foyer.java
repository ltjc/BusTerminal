//This is the foyer class.
//Foyer will be a shared object among all customer from different terminal.
//Foyer is used when any of the waiting area has reached its maximum limit which is 10.
//Once the number of customer is less then 10 the customer that waited the longest will leave the foyer and enter the waiting area as the waiting area is using fair semaphore.
//Foyer is assumed to have no limit on the customer count as the foyer is big and the number of customers in APBT is already controlled by the electric gate.
//Even though there are no limit on the numbers of customers in the foyer. The foyers keep tracks of how many numbers of customers in the foyer.
package BusTerminal;
public class Foyer {
    //Shared count initialised to 0.
    static int count=0;

    //This method will be synchronised as multiple threads will perform addition on the same object.
    synchronized public boolean enterFoyer(Customer c){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;//Race condition will occur here. This problem is resolved by using synchronised block. The synchronised block will ensure there is only 1 thread will have the access to the monitor object and perform the addition. Hence, mutual exclusion can be achieved.
        System.out.println(c.getName()+": Customer has entered the foyer. \t\t\t count: "+count);
        return true;//Indicates that the customer has entered the foyer.
    }

    //This method will be synchronised as multiple threads will perform subtraction on the same object.
    synchronized public boolean leaveFoyer(Customer c){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count--;//Race condition will occur here. This problem is resolved by using synchronised block. The synchronised block will ensure there is only 1 thread will have the access to the monitor object and perform the subtraction. Hence, mutual exclusion can be achieved.
        System.out.println(c.getName()+": Customer has left the foyer. \t\t\t count: "+count);
        return false;//Indicates that the customer has left the foyer.
    }

}
