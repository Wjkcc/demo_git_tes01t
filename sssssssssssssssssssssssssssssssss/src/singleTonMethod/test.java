package singleTonMethod;

public class test {
    public static void main(String[] a){
        single s1=single.getInstance();
        single s2=single.getInstance();
        if(s1==s2){
            System.out.println("yes");
        }else{
            System.out.println("no");
        }
        if(s1.equals(s2)){
            System.out.println("YES");
        }else{
            System.out.println("NO");
        }
    }
}
