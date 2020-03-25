package linked;

public class stack {
    private LinkedList ll;


    public stack(){

        ll=new LinkedList();
    }
    public boolean push(Object obj){
        return ll.addHead(obj);
    }
    public Object pop(){
        return ll.deleteHead();
    }
    public Object peek(){
        return ll.getFirst();
    }
    public boolean isEmpty(){
        return ll.isEmpty();
    }
    public void display(){
        ll.display();
    }


    public static void main(String[] a){
        stack s=new stack();
        s.push("1");
        s.display();
        System.out.println(s.hashCode());
        s.push(1);
        s.push("2");
        System.out.println(s.peek());
        s.pop();
        System.out.println(s.peek());
        s.display();
    }
}
