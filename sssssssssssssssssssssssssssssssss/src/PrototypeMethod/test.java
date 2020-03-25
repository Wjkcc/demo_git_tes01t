package PrototypeMethod;

public class test {
    public static void main(String... arg){
        Manager m=new Manager();
        cd c=new cd("fuck");
        m.register("fuck",c);
        Product p=m.create("fuck");
        p.use();
    }
}
