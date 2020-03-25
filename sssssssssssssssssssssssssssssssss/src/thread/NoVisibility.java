package thread;

public class NoVisibility {
    private static int number;
    private static boolean ready;


    private static class readerThread extends Thread{
        public void run(){
            while(!ready)
                Thread.yield();

            System.out.println(number);
        }
    }
    public static void main(String... arg){
       new readerThread().start();

        number=22;
        ready=true;
    }
}
