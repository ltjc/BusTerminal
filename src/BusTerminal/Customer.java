package BusTerminal;

import java.util.Random;

public class Customer extends Thread{
    static int count;
    int id;
    MainEntrance entrance;
    MainEntrance entrance2;
    TicketMachine tm;
    TicketCounter tc1,tc2;
    Ticket t=null;
    WaitingArea waL;
    WaitingArea waM;
    WaitingArea waR;
    WaitingArea selectedWA;
    Foyer foyer;
    TicketInspector TI;
    boolean enteredWA=false;
    boolean enteredFoyer=false;


    public Customer(MainEntrance e, MainEntrance e2,TicketMachine tm,TicketCounter tc1,TicketCounter tc2,WaitingArea waL,
                    WaitingArea waM,WaitingArea waR,TicketInspector TI,Ticket t){
        this.id=count++;
        this.entrance=e;
        this.entrance2=e2;
        this.tm=tm;
        this.t=t;
        this.tc1= tc1;
        this.tc2= tc2;
        this.waL=waL;
        this.waM=waM;
        this.waR=waR;
        this.TI=TI;
        this.foyer=foyer;
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
            ran= new Random().nextInt(3);
            if (ran==0){//uses the ticket machine
                ticketBool=tm.generateTicket(this);

            }else if (ran==1){//uses the counter 1
                if (tc1.staff.l.tryLock() == true){
                    if (tc1.toiletBreak==false){
                        ticketBool=tc1.printTicket(this); //getting the print ticket
                        System.out.println(this.getName()+": Customer has purchased the ticket.");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(this.getName()+": Customer has left the queue.");
                        tc1.staff.l.unlock();
                    }else { //if toilet break is true, customer will release the lock
                        tc1.staff.l.unlock();
                    }
                }
            }else {// counter 2
                if (tc2.staff.l.tryLock() == true){
                    if (tc2.toiletBreak==false){
                        ticketBool=tc2.printTicket(this); //getting the print ticket
                        System.out.println(this.getName()+": Customer has purchased the ticket.");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(this.getName()+": Customer has left the queue.");
                        tc2.staff.l.unlock();
                    }else { //if toilet break is true, customer will release the lock
                        tc2.staff.l.unlock();
                    }
                }
            }
        }
        //going to departure //maximum of 10 people
        if (t.route =="Shortest"){//setting where the customer go
            selectedWA=waL;
        }else if (t.route =="Medium"){
            selectedWA=waM;
        }else {
            selectedWA=waR;
        }

        while (!enteredWA){
            enteredWA=selectedWA.enter(this); //enter the waiting area
        }
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            selectedWA.leaveWA(this); //leaving the waiting area
        }
        //add departure here

        while (t.scanned==false||t.inspected==false){
            int ranSeq= new Random().nextInt(2);
            if (t.scanned==false&&t.inspected==false){//random when both are not checked
                if (ranSeq==0){//go scan
                    t.scanned= selectedWA.scanMachine.scan(this);// using the scanning machine of the selected waiting area
                }else {// go inspect here
                    TI.inspect(this);
                }
            }else if(t.scanned==false){
                t.scanned= selectedWA.scanMachine.scan(this);// using the scanning machine of the selected waiting area
            }else if(t.inspected==false){
                TI.inspect(this); //inspect here
            }
        }
        System.out.println(getName()+"Customer: Prepare to board "+selectedWA.route+" route bus");
        while (selectedWA.b.count>11){//wait for the bus if the bus is full
            synchronized (selectedWA.b){
                try {
                    selectedWA.b.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (selectedWA.b){ //notify the bus if any customer got in
            selectedWA.b.enterBus();
            System.out.println(getName()+"Customer: Got in!! "+selectedWA.route +" route bus"+"\t\t\tcount: "+selectedWA.b.count);
            if (selectedWA.b.count==12){
                selectedWA.b.notify();
                System.out.println(selectedWA.route+" route Bus is full");
            }else if (selectedWA.b.expired==true){
                selectedWA.b.notify();
                System.out.println(selectedWA.route+" bus will leave due to less customer.");
            }
        }

        while (selectedWA.b.count!=0){
            synchronized (selectedWA.b){//wait for the bus to reach
                try {
                    selectedWA.b.wait();
                }catch (Exception e){}
            }
        }
        entrance.leave(this);

    }



}
