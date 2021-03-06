import org.junit.Test;
import static org.junit.Assert.*;

public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y){
         if(x - y == 1 || y - x == 1){
             return true;
         }
         return false;
    }
    @Test
    public void testEqualChars(){
        assertTrue(equalChars('a','b'));
        assertFalse(equalChars('a','a'));
    }
}
