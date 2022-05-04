import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByN {
    @Test
    public void testEqualChars(){
        OffByN OffBy5 = new OffByN(5);
        System.out.println(OffBy5.equalChars('a','f'));
        assertTrue(OffBy5.equalChars('a','f'));
        assertFalse(OffBy5.equalChars('a','a'));
    }
}
