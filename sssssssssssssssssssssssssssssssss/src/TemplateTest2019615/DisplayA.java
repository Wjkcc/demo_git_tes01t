package TemplateTest2019615;

public class DisplayA extends AbstractDisplay {
    private int a;
    public DisplayA(){
        this.a=2;
    }
    public DisplayA(int a){
        this.a=a;
    }
    public void start(){
        System.out.println("start a="+a);
    }
    public void run(){
        for(int i=0;i<a;i++){
            System.out.println(i);
        }
        a--;
    }
    public void finish(){
        System.out.println("finish a="+a);
    }
    public static void main(String[] args){
        DisplayA d=new DisplayA(3);
        d.display();
    }

}
