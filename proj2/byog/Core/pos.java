package byog.Core;
import java.util.Random;
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

    @Override
    public boolean equals(Object x) {
        if (this == x) return true;
        if (x == null) return false;
        if (this.getClass() != x.getClass()) {
            return false;
        }
        pos that = (pos) x;
        if (this.X != that.X) {
            return false;
        }
        if (this.Y != that.Y) {
            return false;
        }
        return true;
    }


    //check method

}
