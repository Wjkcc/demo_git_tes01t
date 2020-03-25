package linked;

public class LinkedList {
    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    private Node head;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;

    public LinkedList() {
        this.size = 0;
        head = null;
    }

    public boolean addHead(Object obj){

    Node newHead = new Node(obj);
        if(size==0)

    {

        head = newHead;

    }else

    {

        newHead.next = head;
        head = newHead;

    }

    size++;
        return true;
}
        public Object deleteHead(){

            if(size>0){
                Object obj=head.data;
                head=head.next;
                size--;
                return obj;


            }else{
                return null;

        }
    }
    public boolean isEmpty(){
        return size==0;
    }
    public Object getFirst(){
        return (size!=0)?head.data:null;
    }
    public void display(){

        if(size>0){
            Node node=head;
            int tempSize=size;
            if(tempSize==1){
                System.out.println("["+node.data+"]");
                return;
            }
            while(tempSize>0){
                if(node.equals(head)){
                    System.out.print("["+node.data+"->");
                }
                else if(node.next==null){
                    System.out.println(node.data+"]");
                }
                else{
                    System.out.print(node.data+"->");
                }
                node=node.next;
                tempSize--;
            }
        }else{
            System.out.println("[ ]");
        }
    }
    public boolean find(Object obj){
        if(size==0){
            return false;
        }else{
            int tempSize=size;
            Node node=head;
            while(tempSize>0){
                if(node.data.equals(obj)){
                    return true;
                }else{
                    tempSize--;
                    node=node.next;
                }
            }
            return false;
        }
    }
    public boolean delete(Object obj){
        if(size==0){
            return false;
        }
        else{
            if(find(obj)){
                Node node=head;
                int tempSize=size;
                while(tempSize>0){
                        if(head.data.equals(obj)){
                            head=head.next;
                            size--;
                            return true;
                        }else{
                            if(node.next.data.equals(obj)){
                                node.next=node.next.next;
                                size--;
                                return true;
                            }
                        }
                        tempSize--;
                        node=node.next;
                }


            }

                return false;



        }
    }
   protected class Node{
       public Object getData() {
           return data;
       }

       public void setData(Object data) {
           this.data = data;
       }

       public Node getNext() {
           return next;
       }

       public void setNext(Node next) {
           this.next = next;
       }

       private  Object data;
        private Node next;
        public Node(Object obj){
            this.data=obj;
        }

    }
    public static void main(String... arg){
        LinkedList ls=new LinkedList();
        ls.addHead("1");
        ls.addHead("a");
        ls.addHead("a2");
        ls.addHead("a"); ls.addHead("a2");
        ls.addHead("a2");
        ls.display();

        System.out.println(ls.delete("a"));
        ls.display();


    }
}
