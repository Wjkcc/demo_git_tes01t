package model;

public class BookShelf implements Agreegate{
    private Book[] books;
    private int last=0;
    public BookShelf(int maxSize){
        this.books=new Book[maxSize];
    }
    public Book getBookAt(int index){
        return books[index];
    }
    public void appendBook(Book book){
        this.books[last]=book;
        last++;
    }
    public int getLength(){
        return last;
    }
    public Iterator iterator(){
        return new BookShelfIterator(this);
    }
}
