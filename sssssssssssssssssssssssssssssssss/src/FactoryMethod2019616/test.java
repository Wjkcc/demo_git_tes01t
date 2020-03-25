package FactoryMethod2019616;

public class test {
    public static void main(String[] a){
        factory f=new CardFactory();
        product p=f.create("jj");
        product pp=f.create("tt");
        pp.use();
        p.use();
    }
}
