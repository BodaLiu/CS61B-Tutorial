package byog.lab5;
import java.util.Random;
public class testRanodom {
    private static Random RANDOM = new Random(33223);
    public static void main(String[] args) {
       for(int i = 1; i <= 100; i++){
           System.out.println(RANDOM.nextInt(5));
       }


    }
}
