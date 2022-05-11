package byog.Core;
import java.util.ArrayList;
public class pos {
    public int X;
    public int Y;

    public pos(int X, int Y){
        this.X = X;
        this.Y = Y;
    }
    public pos(){
        this.X = 0;
        this.Y = 0;
    }


    //check method
    public static void main(String[] args) {
        String s = "12345";
        long longs =Long.parseLong(s);
        long a = longs + 1;
        System.out.println(a);
    }

}
