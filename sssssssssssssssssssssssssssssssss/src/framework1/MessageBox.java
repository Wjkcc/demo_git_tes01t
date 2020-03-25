package framework1;

public class MessageBox implements Product{
    private char dechar;
    public MessageBox(char dechar){
        this.dechar=dechar;
    }
    public void use(String s){
        int length=s.getBytes().length;
        for(int i=0;i<length;i++){
            System.out.print(dechar);
        }
        System.out.println();
        System.out.println(dechar+" "+s+" "+dechar);
        for(int i=0;i<length;i++){
            System.out.print(dechar);
        }
        System.out.println();
    }
    public Product createClone(){
        Product p=null;
        try{
            p=(Product)clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return p;
    }
}
