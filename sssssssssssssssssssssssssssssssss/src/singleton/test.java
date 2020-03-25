package singleton;

public class test {
    public static void  main(String[] s){
        singletonc s1=singletonc.getInstance();
        singletonc s2=singletonc.getInstance();
        if(s1==s2){
            System.out.println("they are same instance");
        }else{
            System.out.println("they are not same instance");
        }
        System.out.println("this is end");

    }
}
