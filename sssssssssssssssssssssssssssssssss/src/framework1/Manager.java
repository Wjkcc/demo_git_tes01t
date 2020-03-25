package framework1;

import java.util.HashMap;

public class Manager {
    private HashMap showcase=new HashMap();
    public void register(String name,Product p){
        showcase.put(name,p);
    }
    public Product create(String pname){
        Product p=(Product)showcase.get(pname);
        return p.createClone();
    }
}
