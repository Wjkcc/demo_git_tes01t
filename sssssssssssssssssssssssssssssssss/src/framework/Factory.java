package framework;

public abstract class Factory {
    public final Product create(String owner){
            Product p=createProduct(owner);
            regisiterProduct(p);
            return p;
    }
    public abstract Product createProduct(String owner);
    public abstract void regisiterProduct(Product p);


}
