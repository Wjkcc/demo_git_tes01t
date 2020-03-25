package singleTonMethod;

public class single {
    private  static single single=new single();
    private single(){
        System.out.println("dispaly");
    }
    public static single getInstance(){

        return single;
    }
}
