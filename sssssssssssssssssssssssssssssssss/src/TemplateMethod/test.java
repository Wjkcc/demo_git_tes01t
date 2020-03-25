package TemplateMethod;

public class test {
    public static void main(String[] a){
        AbstractDisplay adc=new CharDisplay('s');
        adc.display();
        System.out.println();
        AbstractDisplay ads=new StringDisplay("hello 鼠标");
        ads.display();
    }
}
