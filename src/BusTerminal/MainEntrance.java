//This is the main entrance class.
//There are two main entrance namely south and north.
//Main entrance class is assumed to be a shared object.
//This is where the customer will enter the bus station.
//Due to the pandemic, it is assumed that the main entrance will have an electric gate that keep track of the customers to minimise the operating cost
//The electric gate will block customer from entering if there are more than 100 customer in APBT  and the electric gate will only be open once the customer falls below 30.
//In this class, the sync monitor will be the class itself because the count object is common among all instances
package BusTerminal;
import java.util.Random;

public class MainEntrance {
    String direction;
    static int count=0; //The current count of the customer in the building, this will be static as it is shared among different gate instances.
    static int totalCount=0; //The total count of how many customer has entered the building. Static too to ensure both instances are same
    static boolean blocked=false; //Boolean flag to indicate the entrance is currently blocked.

    MainEntrance(String direction){
        this.direction=direction;
    }

     public boolean enter(Customer c){//Takes in a customer thread.
        synchronized (this.getClass()){ //Synchronisation apply here. The monitor to sync will be the class as there will be 2 main entrance instance. If only sync the method, thread that accessing different instances can stil change the object count.
            try{
                if (count<100&&blocked==false){//If the gate was not blocked and the number of customers is less than 100.
                    System.out.println("Electric Gate" + ": Welcome to APBT!");
                    count++; //Race condition will occur there. The class is sync to ensure only one electric gate can change the count at a time. Thus, mutex can be achieved.
                    System.out.println(c.getName() + ": Customer entered the building through " + direction + " entrance.\t\t\t\t\t Count= " + count);
                    Thread.sleep(new Random().nextInt(100)*10);//Add delay to simulate the real world condition.
                    if (count==100){//if the current number of customer is 100, set the block boolean flag to true.
                        System.out.println("Electric Gate" + ": Please wait, APBT is full.");
                        blocked=true;
                    }
                    return true; //Return true if successfully enter the APBT.
                }
            }catch (Exception e){}
            return false; //Return false if the customer did not manage to get in the APBT.
        }

    }

     public void leave(Customer c){
         synchronized(this.getClass()){//Similar to previous method, this function will sync on the class itself.
             if (count<31&&blocked==true){//If the customer count is less than 31 and the gate is blocked.
                 blocked=false; //Reopen the electric gate.
             }
             count=count-1;//Race condition will occur here. This problem is resolved by using the sync block.
             //counter gets duplicated even though sync is used
             System.out.println(c.getName()+": Left APBT."+"\t\t\t\t\t Remaining Count= "+count);
             if(totalCount++==50) {//Add the total customer count and check if it is equal to the customer thread pool size.Race condition will occur here. Mutex is achieved by the sync block.
                 Main.clear=true; //Set clear boolean from main class to true.
             }
         }

    }

}
