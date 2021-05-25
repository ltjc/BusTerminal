//This is the customer class.
//Customer class is assumed to be a thread as customer in real life can perform the same task simultaneously.
//All shared object/thread will be passed to the customer class and random number will be generate to determine the customer choose which shared object.
//E.g. multiple entrance will be passed to the customer, and the customer will select either one of the entrance at random.
//A customer thread will perform following actions:
//1. Enter from either entrance.
//2. Purchase a ticket from ticket machine or any ticket counter.
//3. Enter Waiting Area and if waiting area is full wait at the foyer.
//4. Get the ticket scanned by the scanning machine and inspected by ticket inspector.
//5. Leave the bus station and board on the bus if the bus has avaliable seat.
//6. Arrive at the destination.

package BusTerminal;
import java.util.Random;
public class Customer extends Thread{//Extending thread to inherit the property of a thread class
    //Defining the attributes of the customer class.
    MainEntrance entrance;
    MainEntrance entrance2;
    MainEntrance selectedEntrance;
    TicketMachine tm;
    TicketCounter tc1,tc2;
    Ticket t;
    WaitingArea waL;
    WaitingArea waM;
    WaitingArea waR;
    WaitingArea selectedWA;
    Foyer foyer;
    TicketInspector TI;
    boolean enteredWA=false; //boolean flag to determin if the customer has entered the waiting area.
    boolean enteredFoyer=false; //boolean flag to determin if the customer has entered the foyer.
    //Object constructor of the customer. All required object is passed to the customer while creating the customer class to ensure all customer are using the same set of shared objects.
    public Customer(MainEntrance e, MainEntrance e2,TicketMachine tm,TicketCounter tc1,TicketCounter tc2,WaitingArea waL,
                    WaitingArea waM,WaitingArea waR,TicketInspector TI){
        this.entrance=e;
        this.entrance2=e2;
        this.tm=tm;
        this.tc1= tc1;
        this.tc2= tc2;
        this.waL=waL;
        this.waM=waM;
        this.waR=waR;
        this.TI=TI;
        this.foyer=foyer;
    }


    public void run(){ //Defining the run method of a thread class.
        boolean r= false; //boolean to check if the customer has successfully entered the APBT.
        int ran= new Random().nextInt(2); //Random number generator to determine which entrance the customer will use.
        //entering the block
        if (ran==0){// If the random nubmer is 0, customer will enter north entance.
            while (r==false){ //While the customer have not enter the building, loop the customer enter method.
                try {
                    r=entrance.enter(this);//Customer enters from north entrance
                    selectedEntrance=entrance;
                    sleep(new Random().nextInt(100)*10); //Sleep the customer thread to simulate the delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else {// If the random nubmer is 0, customer will enter south entance.
            while (r==false){//Loop the enter action if the customer did not manage to get in.
                try {
                    r=entrance2.enter(this);//Customer enters from north entrance
                    selectedEntrance=entrance2;
                    sleep(new Random().nextInt(100)*10);//Sleep the customer thread to simulate the delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        boolean ticketBool=false;//Set ticket boolean flag to indicate whether has the customer managed to purchase a ticket.
        while (ticketBool==false){//Loop the ticket purchase if the customer did not manage to buy a ticket.
            ran= new Random().nextInt(3);//Randomising the ticket purchasing method.
            try {
                sleep(new Random().nextInt(100)*5);//time to move to the selected ticket purchase machine or counter.
            }catch (Exception e){}
            if (ran==0){//Customer uses the ticket machine
                ticketBool=tm.generateTicket(this);//If the customer manage to purchase the ticket, the ticket boolean will set to true.
            }else if (ran==1){//Customer uses the counter 1
                //To ensure only one customer will purchase the ticket at a time, trylock will be used.
                if (tc1.staff.l.tryLock() == true){//The customer will try to obtain the lock from the staff.
                    if (tc1.toiletBreak==false){//If the lock is obtain and the staff is not a toilet break, the customer will purchase the ticket.
                        ticketBool=tc1.printTicket(this); //Purchasing ticket from the ticketing staff.
                        System.out.println(this.getName()+": Customer has purchased the ticket.");
                        try {
                            sleep(1000); //Added delay
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(this.getName()+": Customer has left the queue.");
                        tc1.staff.l.unlock();//Releases the staff's lock.
                    }else { //if the staff is on toilet break, customer will release the lock.
                        tc1.staff.l.unlock();
                    }
                }
            }else {//Customer uses counter 2
                if (tc2.staff.l.tryLock() == true){//If the customer manage to purchase the ticket, the ticket boolean will set to true.
                    if (tc2.toiletBreak==false){//If the lock is obtain and the staff is not a toilet break, the customer will purchase the ticket.
                        ticketBool=tc2.printTicket(this); //Purchasing ticket from the ticketing staff.
                        System.out.println(this.getName()+": Customer has purchased the ticket.");
                        try {
                            sleep(1000);//Added delay
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(this.getName()+": Customer has left the queue.");
                        tc2.staff.l.unlock();//Releases the staff's lock.
                    }else { //Ff toilet break is true, customer will release the lock
                        tc2.staff.l.unlock();//Releases the staff's lock.
                    }
                }
            }
        }
        //At this point the customer has successfully purchased the ticket.
        //Going to waiting area //maximum of 10 people
        //Setting which waiting area the customer will go.
        //Customer is assuemed that they will not go to the wrong station.
        if (t.route =="Shortest"){
            selectedWA=waL;
        }else if (t.route =="Medium"){
            selectedWA=waM;
        }else {
            selectedWA=waR;
        }

        //While the enterWA(a boolean flag that indicates whether if the customer has enter the waiting area) is false, keep trying to enter.
        while (!enteredWA){
            enteredWA=selectedWA.enter(this); //Enter the waiting area, if the waiting area is full, the customer will enter foyer
        }
        try {
            sleep(3000);//Add some delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            selectedWA.leaveWA(this); //Customer will leave the waiting area and head to scanning station
        }
        //At this point the customer is trying to scan or inspect the ticket/
        //Scanning and inspecting can happen at random
        while (t.scanned==false||t.inspected==false){//Loop if either action have not been completed
            if (t.scanned==false&&t.inspected==false){//random when both are not checked
                int ranSeq= new Random().nextInt(2); //Randomisse the scanning and inspecting action.
                if (ranSeq==0){//Customer will scan the ticket.
                    t.scanned= selectedWA.scanMachine.scan(this);// Use the scanning machine of the selected waiting area. This will return true or false as the ticket scanning maching might be in used.
                }else {// Customer will get its ticket inspected
                    TI.inspect(this);
                }
            }else if(t.scanned==false){//If the customer's ticket have not been scan, proceed to scanning.
                t.scanned= selectedWA.scanMachine.scan(this);// Using the scanning machine of the selected waiting area
            }else if(t.inspected==false){//If the customer's ticket have not been inspected, proceed to ticket inspecting.
                TI.inspect(this); //inspect here
            }
        }
        //At this point the customer's ticket is being scanned and inspected.
        //Customer is ready to board the bus.
        System.out.println(getName()+"Customer: Prepare to board "+selectedWA.route+" route bus");
        //Wait and notify is used here to ensure there is only 12 people can board on the bus.
        while (selectedWA.b.count>11){//Customer will wait for the bus if the bus is full. While loop is used instead of if..else because the object might accidentally wake up from the wait command and skips the condiction checker.
            synchronized (selectedWA.b){//Synchronised on the bus object to ensure only one wait can be issue at a time. Avoid accidental loss of wait command as os might fail to register multiple wait.
                try {
                    selectedWA.b.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (selectedWA.b){ //Notify the bus if any customer got in. All notify will only be sync to ensure the will only be one notify command at a time to avoid missing instruction.
            selectedWA.b.enterBus();//Enters the bus
            System.out.println(getName()+"Customer: Got in!! "+selectedWA.route +" route bus"+"\t\t\tcount: "+selectedWA.b.count);
            selectedEntrance.leave(this); //Considered left the bus station
            if (selectedWA.b.count==12){//Customer will notify the bus if the bus is already full
                selectedWA.b.notify();
                System.out.println(selectedWA.route+" route Bus is full");
            }
        }

        while (selectedWA.b.count!=0){//Loop until the bus has 0 customer. In other words, wait until the bus has fetched all customers.
            synchronized (selectedWA.b){//Sync on the shared object which is the bus and ensure one wait command issue at a time.
                //Wait for the bus to reach the destination.
                //In this simulation, the customer is considered reached the destination once the bus driver has fetched all customers and drove througth all routes.
                try {
                    selectedWA.b.wait();
                }catch (Exception e){}
            }
        }
        //All customer tasks have been completed. The customer thread will be terminated.
    }



}
