package BusTerminal;

public class Guard extends Staff{
    MainEntrance dutyPosition;

    Guard(){ }

    Guard(MainEntrance dutyPosition){
        this.dutyPosition=dutyPosition;
    }


    public void run(){
        while (!Main.clear){
            if (MainEntrance.count==99){
                MainEntrance.blocked=true;
                System.out.println(this.getName()+" Guard: no entry");
            }else if (MainEntrance.count<31&&MainEntrance.blocked==true){
                MainEntrance.blocked=false;
            }
        }
    }

}
