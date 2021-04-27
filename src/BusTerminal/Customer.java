package BusTerminal;

import javax.print.attribute.standard.RequestingUserName;
import java.util.Random;

public class Customer extends Thread{
    static int count;
    int id;
    MainEntrance entrance;
    MainEntrance entrance2;

    public Customer(MainEntrance e, MainEntrance e2){
        this.id=count++;
        this.entrance=e;
        this.entrance2=e2;
    }


    public void run(){
        boolean r= false;
        int ran= new Random().nextInt(2);

        //entering the block

        if (ran==0){
            try {
                entrance.enter(this);
                sleep(new Random().nextInt(1000)*100);
                entrance.leave(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            try {
                entrance2.enter(this);
                sleep(new Random().nextInt(1000)*100);
                entrance2.leave(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
}
