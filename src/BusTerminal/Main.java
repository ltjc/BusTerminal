package BusTerminal;

import java.util.Random;

public class Main {
    static boolean clear=false;

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
        WaitingArea waL= new WaitingArea(foyer,"Left",sm1,b1);
        WaitingArea waM= new WaitingArea(foyer,"Middle",sm2,b2);
        WaitingArea waR= new WaitingArea(foyer,"Right",sm3,b3);
        TicketInspector TI= new TicketInspector();


        for(int i=0;i<150;i++){
            c[i]=new Customer(e1,e2,tm,tc1,tc2,waL,waM,waR,TI,t);

        }

        for(int i=0;i<150;i++){//TODO change this later to accommodate more people
            c[i].start();
        }
        tc1.staff.start();
        tc2.staff.start();
        TI.start();

        try {
            for(int i=0;i<150;i++){//TODO change this later to accommodate more people
                c[i].join();
                TI.join();
            }
            tc2.staff.start();
        }catch (Exception e){}


    }
}

