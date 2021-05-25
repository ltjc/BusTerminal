//This is the bus class.
//Bus is assumed to be a shared object for the customers.
//There will be three buses in this simulation. Each bus is assigned to one route.
//All bus will have a counter to keep track of the numbers of customer currently in the bus.
//A bus will have a maximum capacity of 12 customers.

package BusTerminal;
import java.util.Random;
public class Bus{
    //Shared count has been initialised to 0.
    //This object will not be static as each bus will have its own counter.
    int count=0;
    final static int max= 12; //Final static maximum count for all bus.
    WaitingArea wa;
    long currentTime = System.currentTimeMillis();//Set the initial time for the bus

    public void enterBus(){//This method is called when the customers are entering the bus.
        //Race condition might occur here as multiple threads will attempt to change the counter at the same time.
        //However, there were no synchronised techniques apply here because this this method will be enclosed by a sync block while calling the method.
        //In the customer thread, the customer will use the bus instance as the synchronised monitor and perform the addition.
        //The reason of that is because, this method is called with the notify() method. And these methods are enclosed with a sync block that uses the bus instance as the monitor.
        //Thus, the mutex can be achieved and adding another synchronised block here will be redundant.
        count++;//Race condition will occur here.

    }

    //This method is called the the bus is departing.
    //The departure will also consider the bus break down.
    //The bus is assumed to have a 5% chance to break down.
    //If the bus has broken down, it is expected the bus driver will take double the duration to complete the route.
    public void depart(int originalDuration,BusDriver bd){//Pass in the duration of the route and the bus driver.
        int ran= new Random().nextInt(100); //Randomise the chance of break down.
        System.out.println(bd.getName()+"Bus Driver: The"+wa.route +"route bus has departed.");
        try{
            if (ran>94){ //5% break down. As the ran int will only random from 0 to 99.
                System.out.println(bd.getName()+"Bus Driver: The"+wa.route +"route bus has broken down. Please expect delay.");
                Thread.sleep(originalDuration*2); //The bus driver thread (thread that runs this method) will be put to sleep for double the duration.
                System.out.println(bd.getName()+"Bus Driver: The"+wa.route +"route bus has fetched all customers.");
            }else{
                Thread.sleep(originalDuration); //If the bus does not break down.
                System.out.println(bd.getName()+"Bus Driver: The"+wa.route +"route bus has fetched all customers.");
            }
        }catch (Exception e){}
    }


}
