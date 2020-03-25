package linked;

public class DoubleLinkeList extends LinkedList{
    private Node tail;
    public DoubleLinkeList(){
        super();
        tail=null;
    }
    @Override
    public boolean addHead(Object obj){
        Node node=new Node(obj);
        if(isEmpty()){
          getHead().setData(obj);
          tail=getHead();



        }
        else{
            node.setNext(getHead());
            setHead(node);
        }
        int s=getSize();
        s++;
        setSize(s);
        return true;
    }
    public boolean deleteTail(){
        if(isEmpty()){
            return false;

        }
       else{
           tail=null;
        }
        int s=getSize();
        s--;
        setSize(s);
        return true;

    }
    public static void main(String[] a){
        int as=1/2;
        System.out.println(as);
    }
}
