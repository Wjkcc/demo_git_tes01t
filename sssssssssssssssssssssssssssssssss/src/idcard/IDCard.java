package idcard;

import framework.Product;

public class IDCard extends Product {
    private String owner;
    public IDCard(String owner)
    {
        System.out.println("制作了"+owner+"的IDCard");
        this.owner=owner;
    }
    public void use(){
        System.out.println("使用了"+owner+"的IDCard");
    }
    public String getOwner(){
        return owner;
    }
}
