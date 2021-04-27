package BusTerminal;

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
            while (r==false){
                //randomise entrance

                try {
                    r=entrance.enter(this);
                    sleep(new Random().nextInt(10)*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            entrance.leave(this);
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
            entrance2.leave(this);
        }















    }
}
