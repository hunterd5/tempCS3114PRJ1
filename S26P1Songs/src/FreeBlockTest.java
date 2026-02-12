import static org.junit.Assert.*;
import org.junit.Test;

/**
 * A single linked list holding free blocks within the memory manager
 *
 * @author Katelyn Cao, Hunter Dillon
 * @version feb 6, 2026
 */

public class FreeBlockTest
{

    // ----------------------------------------------------------
    /**
     * tests that testBlock initializes correctly
     */
    public void testInitialization()
    {
        FreeBlock testBlock = new FreeBlock(1, 10);
        assertEquals(testBlock.start, 1);
        assertEquals(testBlock.size, 10);
    }

}
