package BusTerminal;

import java.util.Random;

public class BusDriver extends Thread{
    Bus b;
    private int waitingDuration; //waiting duration
    long startTime=System.currentTimeMillis();
    long elapsedTime=0;
    BusDriver(Bus b){
        this.b=b;
    }

    public void run(){
        while (true){
            //add timer here
            while (b.count<12&&System.currentTimeMillis()-b.currentTime<10000){ //if the number of customer is less than 12 and the bus did not wait more than 10 since the last custoemr
                synchronized(b)
                {
                    try{
                        b.wait(4000);//wake up the bus to check the condition
                        if (b.expired==true){
                            break;
                        }
                    }catch (Exception e){}
                }
            }
//            if (System.currentTimeMillis()-b.currentime<10000){
//                System.out.println(getName()+"Bus Driver: Bus will depart early due to less customer.");
//            }
            synchronized(b)
            {
                if (b.count>0){
                    try {
                        if (b.wa.route =="Shortest"){//5% break down, if delay double the orginal waiting time
                            //takes 4-5 second for shortest trip,
                            waitingDuration= 4000+new Random().nextInt(100);
                            b.depart(waitingDuration,this);
                            b.count=0;
                            b.notifyAll(); //notify the customer that the bus is empty


                        }else if (b.wa.route =="Medium"){
                            //takes 15 to 20 second for medium trip
                            waitingDuration= 1500+new Random().nextInt(500);
                            b.depart(waitingDuration,this);
                            b.count=0;
                            b.notifyAll();

                        }else if (b.wa.route =="Furthest"){
                            //takes 30 to 45 second for long trip
                            waitingDuration= 3000+new Random().nextInt(1500);
                            b.depart(waitingDuration,this);
                            b.count=0;
                            b.notifyAll();
                        }
                    }catch (Exception e){}
                }
            }


        }
    }


}
