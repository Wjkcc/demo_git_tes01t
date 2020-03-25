package FactoryMethod2019616;

public class Card implements product{
    private String name;
    public Card(String name){
        this.name=name;
    }
  public void use()
    {
        System.out.println("use"+name);
    }

}
