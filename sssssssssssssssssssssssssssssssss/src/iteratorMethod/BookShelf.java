package iteratorMethod;

public class BookShelf implements Ag{
    private book[] books;
    private int index;
    public BookShelf(int size){
        books=new book[size];
        this.index=0;
    }
    public boolean add(book book){

        books[index++]=book;
        return true;
    }
    public int getLength(){
        return index;
    }
    public book[] getBooks(){
        return books;
    }
    public Iterator createIterator(){
        return new BookShelfIterator(this);
    }

}
