package FactoryMethod2019616;

public abstract class factory {
    public abstract product create(String name);
    public product createProduct(String name){
        return create(name);
    }

}
