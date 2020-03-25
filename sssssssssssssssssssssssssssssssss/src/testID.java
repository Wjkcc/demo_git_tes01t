import framework.Factory;
import framework.Product;
import idcard.IDCardFactory;

public class testID {
    public static void main(String[] a){
        Factory factory=new IDCardFactory();
        Product p=factory.create("nm");
        p.use();

    }
}
