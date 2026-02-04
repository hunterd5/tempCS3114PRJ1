import static org.junit.Assert.*;
import org.junit.Test;

public class FreeBlockTest {

    @Test
    public void testInitialization() {
        FreeBlock testBlock = new FreeBlock(1, 10);
        assertEquals(testBlock.start, 1);
        assertEquals(testBlock.size, 10);
    }

}
