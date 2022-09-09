import java.util.ArrayList;

public class Play {
    public static void main(String[] args) {
        int i = 0;
//        for (ArrayList<Integer>;;)
//        {
//            System.out.println("hello");
//            i++;
//        }

        for (ArrayList<Integer> a = new ArrayList();;){
            System.out.println("hello");
        }
    }
    static void a(){}
    static int b(){return 1;}

    int c(int x) {
        if (x<10) return 1;
        else return 3;
    }
}

interface a1 {}
interface a2 {}

interface a3 extends a1,a2 {
    void a1();
    static int a = 0;

}