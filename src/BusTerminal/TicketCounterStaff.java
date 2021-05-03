package BusTerminal;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketCounterStaff extends Staff{

    TicketCounter tc;
    Customer c;
    Lock l = new ReentrantLock(true); //fair lock

    public TicketCounterStaff(TicketCounter counter) {
        this.tc=counter;
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(30000+new Random().nextInt(15000)); //30 second to 45 second
                synchronized (tc){ //sync to ensure no loss of data
                    tc.toiletBreak=true;
                }
                while (l.tryLock()==false){ //try until it get the lock
                    Thread.sleep(1000); //wait 1 second to check the ticket again
                    System.out.println(getName()+" ticket counter staff: Toilet break soon."); //try to get lock first
                }
                //acquired the lock
                System.out.println(getName()+" ticket counter staff: Toilet break!!");
                for(int i=0;i<9;i++){
                    System.out.println(getName()+" ticket counter staff: Toilet break..."+"...".repeat(i));
                    Thread.sleep(1000);// break 5 to 10 second
                }
                System.out.println(getName()+" ticket counter staff: Back from break!!");
                tc.toiletBreak=false;
                l.unlock();// release the lock
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




//    int ranToilet=new Random().nextInt(101);
//    if (ranToilet>90){
//        System.out.println(this.getName() + " is heading to toilet.");
//        tc.toiletBreak=true;
//        Thread.sleep(5000);//wait for 5second
//        System.out.println(this.getName() + " is back from toilet.");
//        tc.toiletBreak=false;

//      System.out.println("Staff is going for a toilet break.");
//      System.out.println(c.getName()+" buy the ticket from other option");
//        int ran= new Random().nextInt(3);
//        if (ran==0){ //left
//            return true;
//        }else if (ran==1){//middle
//            return true;
//        }else {//right
//            return true;
//        }



    }
}

