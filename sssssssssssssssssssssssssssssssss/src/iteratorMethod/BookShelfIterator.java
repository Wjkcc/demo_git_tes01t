package iteratorMethod;

public class BookShelfIterator implements Iterator{
    private BookShelf bookShelf;
    int index;
    public BookShelfIterator(BookShelf bookShelf){
        this.bookShelf=bookShelf;
        this.index=0;
    }
    public boolean hasNext(){
        if(index<bookShelf.getLength()){

            return true;

        }
        else{
            return false;
        }
    }
    public Object next(){
        return bookShelf.getBooks()[index++];
    }
}
