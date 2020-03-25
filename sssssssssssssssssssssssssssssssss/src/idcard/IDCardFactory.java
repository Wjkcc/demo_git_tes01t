package idcard;

import framework.Factory;
import framework.Product;

import java.util.ArrayList;
import java.util.List;

public class IDCardFactory extends Factory {
    private List owners=new ArrayList();
    public Product createProduct(String owner){
        return new IDCard(owner);
    }
    public  void regisiterProduct(Product p){
            owners.add(((IDCard)p).getOwner());
    }
    public List getOwners(){
        return owners;
    }
}
