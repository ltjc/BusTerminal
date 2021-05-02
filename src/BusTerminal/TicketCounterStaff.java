package BusTerminal;

import java.util.Random;

public class TicketCounterStaff extends Staff{

    TicketCounter tc;



    public TicketCounterStaff(TicketCounter counter) {
        this.tc=counter;
    }

    public void run(){
        for(int i=0;i<tc.customerAL.size();i++){
            Customer c= tc.customerAL.get(i);
            while (true){
                try {
                    while (!tc.occupied){
                        synchronized (c){
                            c.wait();
                            Thread.sleep(1000);//wait for 1sec to ask other customer
                            System.out.println(this.getName()+"Next please!");
                        }
                    }
                    synchronized (tc){
                        tc.occupied=true;
                        System.out.println(this.getName() + " is printing a ticket for the customer.");
                        int ran= new Random().nextInt(3);
                        if (ran==0){
                            c.t = new Ticket("Left");
                        }if (ran==1){
                            c.t = new Ticket("Middle");
                        }else {
                            c.t = new Ticket("Right");
                        }

                        synchronized (this){
                            this.notify(); //
                            System.out.println("Ticket has been printed.");
                            int ranToilet=new Random().nextInt(101);
                            if (ranToilet>90){
                                System.out.println(this.getName() + " is heading to toilet.");
                                tc.toiletBreak=true;
                                Thread.sleep(5000);//wait for 5second
                                System.out.println(this.getName() + " is back from toilet.");
                                tc.toiletBreak=false;
                            }
                        }
                    }

                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

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
}
