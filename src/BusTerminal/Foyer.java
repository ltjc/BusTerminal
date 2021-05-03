package BusTerminal;

public class Foyer {
    static int count=0;

    synchronized public boolean enterFoyer(Customer c){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        System.out.println(c.getName()+": Customer has entered the foyer. \t\t\t count: "+count);
        return true;
    }

    synchronized public boolean leaveFoyer(Customer c){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count--;
        System.out.println(c.getName()+": Customer has left the foyer. \t\t\t count: "+count);
        return false;
    }

}
