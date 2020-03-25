package framework1;

public class test {
    public static void main(String a[]){
        Manager m=new Manager();
        MessageBox ms=new MessageBox('w');
        m.register("ms",ms);
        Product p1=m.create("ms");
        p1.use("www");
    }
}
