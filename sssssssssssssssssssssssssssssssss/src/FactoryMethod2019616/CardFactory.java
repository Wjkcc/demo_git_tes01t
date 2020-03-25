package FactoryMethod2019616;

import java.util.Date;

public class CardFactory extends factory{
    public product create(String name){
        System.out.println("create"+name+ new Date());
        return new Card(name);
    }
    public product createProduct(String name){
        return create(name);
    }
}
