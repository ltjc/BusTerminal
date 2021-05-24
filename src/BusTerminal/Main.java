//This is the main class of the bus terminal system.
//In this class all threads and shared objects will be created and initialised.
//In addition, all thread class will be started in this class too.
//Lastly, the join method is called for all threads to ensure the main thread wait until the termination of all threads
package BusTerminal;

public class Main {
    static boolean clear=false; //universal static boolean flag to

    public static void main(String[] args) {
        Guard g1= new Guard();
        Guard g2= new Guard();
        Ticket t= null;
        TicketMachine tm= new TicketMachine();
        TicketCounter tc1=new TicketCounter(1);
        TicketCounter tc2=new TicketCounter(2);
        TicketCounterStaff tcStaff1= new TicketCounterStaff(tc1);
        TicketCounterStaff tcStaff2= new TicketCounterStaff(tc2);
        tc1.staff=tcStaff1;
        tc2.staff=tcStaff2;
	    MainEntrance e1= new MainEntrance("North",g1);
        MainEntrance e2=new MainEntrance("South",g2);
        Customer c[]= new Customer[150];
        Foyer foyer= new Foyer();
        ScanningMachine sm1=new ScanningMachine();
        ScanningMachine sm2=new ScanningMachine();
        ScanningMachine sm3=new ScanningMachine();
        Bus b1= new Bus();
        Bus b2= new Bus();
        Bus b3= new Bus();
        BusDriver bd1= new BusDriver(b1);
        BusDriver bd2= new BusDriver(b2);
        BusDriver bd3= new BusDriver(b3);
        WaitingArea waL= new WaitingArea(foyer,"Shortest",sm1,b1);
        WaitingArea waM= new WaitingArea(foyer,"Medium",sm2,b2);
        WaitingArea waR= new WaitingArea(foyer,"Furthest",sm3,b3);
        b1.wa=waL;
        b2.wa=waM;
        b3.wa=waR;
        TicketInspector TI= new TicketInspector();


        for(int i=0;i<50;i++){
            c[i]=new Customer(e1,e2,tm,tc1,tc2,waL,waM,waR,TI,t);

        }

        for(int i=0;i<50;i++){//TODO change this later to accommodate more people
            c[i].start();
        }
        tc1.staff.start();
        tc2.staff.start();
        TI.start();
        bd1.start();
        bd2.start();
        bd3.start();

        try {
            for(int i=0;i<50;i++){//TODO change this later to accommodate more people
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

