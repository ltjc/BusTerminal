//This is the main class of the bus terminal system.
//In this class all threads and shared objects will be created and initialised.
//In addition, all thread class will be started in this class too.
//Lastly, the join method is called for all threads to ensure the main thread wait until the termination of all threads
package BusTerminal;

public class Main {
    static boolean clear=false; //universal static boolean flag to stop the programme after all customer leaves
    public static void main(String[] args) {
        TicketMachine tm= new TicketMachine(); //ticket machine will be a shared object
        TicketCounter tc1=new TicketCounter(1); //Initialising ticket counter 1 and 2
        TicketCounter tc2=new TicketCounter(2);
        TicketCounterStaff tcStaff1= new TicketCounterStaff(tc1); //creating ticket counter staff, ticket counter staff will be a thread
        TicketCounterStaff tcStaff2= new TicketCounterStaff(tc2);
        tc1.staff=tcStaff1; //Each staff will be assigned to their respective counter and each counter has only 1 staff.
        tc2.staff=tcStaff2;
	    MainEntrance e1= new MainEntrance("North"); //Initialising main entrance and main entrance will be shared object.
        MainEntrance e2=new MainEntrance("South");
        Customer c[]= new Customer[150]; //Creating a pool of customer thread.
        Foyer foyer= new Foyer(); //Initialising the foyer object and the foyer will be a shared object.
        ScanningMachine sm1=new ScanningMachine(); //Initialising ticketing scanning machine, each station will have a ticket scanning machine
        ScanningMachine sm2=new ScanningMachine(); //Ticket scanning machine will act as a shared object.
        ScanningMachine sm3=new ScanningMachine();
        Bus b1= new Bus(); //Initialising 3 new buses, each bus is assigned to one route/station and bus will be shared object.
        Bus b2= new Bus();
        Bus b3= new Bus();
        BusDriver bd1= new BusDriver(b1); //Initialising three bus driver, each bus driver is assigned to one station and bus driver will be thread.
        BusDriver bd2= new BusDriver(b2);
        BusDriver bd3= new BusDriver(b3);
        WaitingArea waL= new WaitingArea(foyer,"Shortest",sm1,b1); //Initialising three waiting area, each route will have a waiting area.
        WaitingArea waM= new WaitingArea(foyer,"Medium",sm2,b2); //Waiting area is assumed to be a shared object.
        WaitingArea waR= new WaitingArea(foyer,"Furthest",sm3,b3);
        b1.wa=waL; // Assigning each bus to a waiting area, since each bus will be assigned to each route.
        b2.wa=waM;
        b3.wa=waR;
        TicketInspector TI= new TicketInspector(); //Initialising the ticket inspector thread.


        for(int i=0;i<50;i++){
            c[i]=new Customer(e1,e2,tm,tc1,tc2,waL,waM,waR,TI); //assigning all shared object/thread to the customer
        }

        for(int i=0;i<50;i++){//starting the customer thread pool
            c[i].start();
        }

        tc1.staff.start(); //starting the staff thread
        tc2.staff.start();
        TI.start(); //starting the ticket inspector thread
        bd1.start(); //starting the bus driver thread
        bd2.start();
        bd3.start();

        try {
            for(int i=0;i<50;i++){//Joining all customer threads to ensure the main thread wait for the execution of all thread before it terminates
                c[i].join();
            }
            TI.join();
            tc1.staff.join();
            tc2.staff.join();
            bd1.join();
            bd2.join();
            bd3.join();
        }catch (Exception e){}
    }



}


