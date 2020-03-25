package framework1;

public interface Product extends Cloneable{
    void use(String s);
   Product createClone();
}
