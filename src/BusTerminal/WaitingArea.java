package BusTerminal;

import java.util.concurrent.Semaphore;

public class WaitingArea {
    Semaphore limWait= new Semaphore(10, true);
    String direction;
    Foyer foyer;
    ScanningMachine scanMachine;
    Bus b;


    WaitingArea(Foyer foyer,String Direction,ScanningMachine scanMachine,Bus b){
        this.foyer=foyer;
        this.direction=Direction;
        this.scanMachine=scanMachine;
        this.b=b;
    }

    public boolean enter(Customer c){
        if (limWait.availablePermits()==0){ //if no permit
            System.out.println(c.getName()+" Customer: Waiting area is full. \t\t");
            System.out.println(c.getName()+" Customer: heading to the foyer. \t\t");
            c.enteredFoyer=foyer.enterFoyer(c);
            return false;
        }
        try {
            //todo try acquire to get the permit and ensure 40% of people left waiting area
            limWait.acquire();
            if (c.enteredFoyer==true){//if the customer has acquired the permit and currently in foyer
                c.enteredFoyer=foyer.leaveFoyer(c);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(c.getName()+"Customer: has enter the waiting area "+c.selectedWA.direction+ ".\t\t"+limWait.availablePermits()+" left left in waiting area"+c.selectedWA.direction);
        return true;
    }

    public void leaveWA(Customer c){
        limWait.release();
        System.out.println(c.getName()+" Customer: Heading to the departure gate "+c.selectedWA.direction+ ".\t\t"+limWait.availablePermits()+" left in waiting area"+c.selectedWA.direction);
    }

}
