package BusTerminal;

import java.util.Random;

public class Bus{
    int count=0;
    final static int max= 12;
    Customer c;
    BusDriver bd;
    WaitingArea wa;
    long waitTime,currentTime;
    boolean expired;

    public void enterBus(){
        count++;
        currentTime=System.currentTimeMillis();
        if(count==1){
            startTimer();
            expired=false;
        }

        if (currentTime-waitTime>50000){ //if last customer found that its waited more than 50sec
            expired=true;
        }
    }

    public void depart(int originalDuration,BusDriver bd){//bus break down
        int ran= new Random().nextInt(100);
        expired=false;
        System.out.println(bd.getName()+"Bus Driver: The"+wa.route +"route bus has departed.");
        try{
            if (ran>94){ //5% break down
                System.out.println(bd.getName()+"Bus Driver: The"+wa.route +"route bus has broken down. Please expect delay.");
                Thread.sleep(originalDuration*2);
                System.out.println(bd.getName()+"Bus Driver: The"+wa.route +"route bus has fetched all customers.");
            }else{
                Thread.sleep(originalDuration); //original duration
                System.out.println(bd.getName()+"Bus Driver: The"+wa.route +"route bus has fetched all customers.");
            }
        }catch (Exception e){}

    }

    synchronized public void startTimer(){
        waitTime=System.currentTimeMillis();
    }


}
