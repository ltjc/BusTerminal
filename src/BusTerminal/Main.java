package BusTerminal;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Guard g1= new Guard();
        Guard g2= new Guard();
	    MainEntrance e1= new MainEntrance("North",g1);
        MainEntrance e2=new MainEntrance("South",g2);
        Customer c[]= new Customer[150];

        for(int i=0;i<150;i++){
            c[i]=new Customer(e1,e2);
        }

        for(int i=0;i<150;i++){
            c[i].start();
        }
        try {
            for(int i=0;i<150;i++){
                c[i].join();
            }
        }catch (Exception e){}


    }
}
