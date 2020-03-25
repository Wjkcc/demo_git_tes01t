package PrototypeMethod;

import java.util.HashMap;

public class Manager {
    private HashMap hashMap=new HashMap();
    public void register(String name,Product product){
        hashMap.put(name,product);
    }
    public Product create(String name){

        Product p=(Product) hashMap.get(name);
        return p.createClone();
    }
}
