package iteratorMethod;

public class test {
    public static void main(String[] a){
        BookShelf bf=new BookShelf(10);
        bf.add(new book("123"));
        bf.add(new book("1234"));
        bf.add(new book("1235"));
        bf.add(new book("1236"));
        Iterator it=bf.createIterator();
        while(it.hasNext()){
            book book=(book)it.next();
            System.out.println("name is"+book.getName());
        }

    }
}
