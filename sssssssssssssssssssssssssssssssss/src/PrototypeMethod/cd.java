package PrototypeMethod;

public class cd implements Product{
    private String name;
    public cd(String name){
        this.name=name;
        System.out.println("init cd :"+name);
    }
    public void use(){
        System.out.println();
        int length=name.getBytes().length;
        for(int i=0;i<length;i++){
            System.out.print("+");
        }
        System.out.println();
        for(int i=0;i<5;i++){
            System.out.println("name");
        }
        System.out.println();
        for(int i=0;i<length;i++){
            System.out.print("+");
        }
        System.out.println();
        System.out.println();
    }

    @Override
    public Product createClone() {
        Product p=null;
      try{
          p=(cd)clone();
      }catch (Exception e){
          e.printStackTrace();
      }
      return p;
    }
}
