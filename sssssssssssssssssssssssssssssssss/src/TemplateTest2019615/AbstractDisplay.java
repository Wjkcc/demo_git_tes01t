package TemplateTest2019615;

public abstract class AbstractDisplay {

    public abstract void start();
    public abstract void run();
    public abstract void finish();
    public void display(){
        start();
        run();
        finish();

    }
}
