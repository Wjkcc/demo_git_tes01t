package singleton;

public class singletonc {
    private static singletonc singletoc=new singletonc();
    private singletonc(){
        System.out.println(" produce a new instance!");

    }
    public static singletonc getInstance(){
        return singletoc;
    }

}
