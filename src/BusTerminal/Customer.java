package BusTerminal;

import java.util.Random;

public class Customer extends Thread{
    static int count;
    int id;
    MainEntrance entrance;
    MainEntrance entrance2;
    TicketMachine tm;
    TicketCounter tc;
    Ticket t=null;

    public Customer(MainEntrance e, MainEntrance e2,TicketMachine tm,TicketCounter tc,Ticket t){
        this.id=count++;
        this.entrance=e;
        this.entrance2=e2;
        this.tm=tm;
        this.t=t;
        this.tc= tc;
    }


    public void run(){
        boolean r= false;
        int ran= new Random().nextInt(2);

        //entering the block
        if (ran==0){
            while (r==false){
                //randomise entrance
                try {
                    r=entrance.enter(this);
                    sleep(new Random().nextInt(10)*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //entrance.leave(this);
        }else {
            while (r==false){
                //randomise entrance

                try {
                    r=entrance2.enter(this);
                    sleep(new Random().nextInt(10)*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //entrance2.leave(this);
        }

        boolean ticketBool=false;
        while (ticketBool==false){
            try {//time to move to other place or consider to stay
                sleep(new Random().nextInt(1000)*5);
            }catch (Exception e){}
            ran= new Random().nextInt(2);
            if (ran==0){//uses the ticket machine
                if (tc.staff.l.tryLock() == true){
                    if (tc.toiletBreak==false){
                        ticketBool=tc.printTicket(this); //getting the print ticket
                        System.out.println(this.getName()+": Customer has purchased the ticket.");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(this.getName()+": Customer has left the queue.");
                        tc.staff.l.unlock();
                    }else { //if toilet break is true, customer will release the lock
                        tc.staff.l.unlock();
                    }
                }
            }else if (ran==1){//uses the counter 1
                ticketBool=tm.generateTicket(this);
            }else {// counter 2

            }
        }
        entrance.leave(this);
























    }



}
