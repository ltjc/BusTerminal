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
            ran= new Random().nextInt(1);

            if (ran==0){//uses the ticket machine
                //ticketBool=tm.generateTicket(this);
                while (true){
                    if (!tc.toiletBreak){//if toilet break customer will leave
                        try{
                            while (tc.occupied){//wait for the ticket counter staff
                                synchronized (tc.staff){
                                    tc.staff.wait();
                                }
                                System.out.println(this.getName()+" is waiting for his/her turn.");
                            }
                            synchronized (tc){
                                tc.occupied=false;
                                System.out.println(tc.staff.getName() + " paying for the ticket.");
                                synchronized (this){
                                    this.notify(); //notify itself that the task has been finished
                                    System.out.println(this.getName() + " has left the queue.");
                                }
                            }
                        }catch (Exception e){}
                    }else {
                        break;
                    }

                }
            }else if (ran==1){//uses the counter 1

            }else {// counter 2

            }
        }
        entrance.leave(this);
























    }



}
