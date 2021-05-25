//This is the bus  driver class.
//Bus driver is assumed to be a thread class.
//There will be three bus driver and each bus driver is assigned to a route.
//The bus object will be shared among the bus driver thread and the customer thread.
//The bus driver will depart if:
//1. Number of customer on the bus is 12
//2. The bus has waited more than 10 second
//To implement these rules, wait, notify and notify all will be used.
//In addition, it is assumed that the time taken for each route is the time needed to visit all stations and return to the APBT.

package BusTerminal;
import java.util.Random;

public class BusDriver extends Thread{//Extending thread class to inherit all thread properties.
    Bus b;
    private int waitingDuration; //waiting duration
    BusDriver(Bus b){
        this.b=b;
    } //Passing a bus to a bus driver since each station will have a bus driver and bus.

    public void run(){//Run method for the thread.
        while (!Main.clear){//While the boolean clear flag in the main class is not true.
            while (b.count<12&&System.currentTimeMillis()-b.currentTime<10000){ //Wait for the bus if the number of customer in the bus is less than 12 and the bus have not stayed more than 10 second.
                //Concurrency concept- Synchronised
                //The synchronised block will sync on the bus monitor to ensure only one wait is issued at a time.
                // Without the sync, customer threads and bus driver thread might access the bus monitor at a time and issue multiple wait. This might cause loss of data.
                //This problem is resolved by synchronise on the bus monitor object.
                synchronized(b)
                {
                    try{
                        b.wait(5000);//Time out duration is set to 1 so that the bus object will wake itself up and check whether the bus has stayed for more than 10 seconds.
                    }catch (Exception e){}
                }
            }

            //At this point the bus will depart from APBT.
            //Concurrency concept- Synchronisation.
            //Similar to previous sync, this block of code will sync on the bus object.
            //Bus has 5% break down, if it breaks down, it will double the original waiting time.
            synchronized(b) //Bus will be used at the monitor object.
            {
                try {
                    if (b.wa.route =="Shortest"){//Shortest Route
                        //takes 4-5 second for shortest trip,
                        waitingDuration= 4000+new Random().nextInt(100);
                        b.depart(waitingDuration,this);//Bus will depart from the location.
                        b.count=0;//Race condition might occur here as the customer might read the old value and perform incorrect actions. This type of race condition also being referred as check then act problem.
                        b.currentTime=System.currentTimeMillis();//Even though this might seems like a race condition, but not race condition will occur here as the object is not shared with other threads. Only one bus driver will have change and check the value here.
                        //However, even if multiple threads will shared this value in future, the race condition will not happens too as the instruction is encapsulated with a sync block.
                        b.notifyAll(); //Notify all customer that is waiting for the bus.
                        //This simulation does not consider each individual stops of the bus. Thus, customers is assumed to reach their destination once the bus has reached APBT again.
                        //There are two type of customer that waits for the bus to be empty.
                        //1. Customer that wants to get in the bus.
                        //2. Customer that is currently in the bus and waiting to reach the destination.

                    }else if (b.wa.route =="Medium"){
                        //Takes 15 to 20 second for medium trip
                        waitingDuration= 1500+new Random().nextInt(500);
                        b.depart(waitingDuration,this);
                        b.count=0;
                        b.currentTime = System.currentTimeMillis();
                        b.notifyAll();
                    }else if (b.wa.route =="Furthest"){
                        //Takes 30 to 45 second for long trip
                        waitingDuration= 3000+new Random().nextInt(1500);
                        b.depart(waitingDuration,this);
                        b.count=0;
                        b.currentTime = System.currentTimeMillis();
                        b.notifyAll();
                    }
                }catch (Exception e){}
            }
            if (Main.clear) { //Check if the boolean flag in main class is true.
                break; //If the clear boolean flag is true, it indicates that all customers has left APBT and this thread will break the while loop.
            }
        }
    }

}
