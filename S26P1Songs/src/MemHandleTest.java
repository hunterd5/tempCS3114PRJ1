import static org.junit.Assert.*;
import org.junit.Test;

public class MemHandleTest
{

    // ----------------------------------------------------------
    /**
     * tests that the constructor creates a handle correctly and that get
     * methods return the correct values
     */
    public void testConstructorAndGetters()
    {
        // Create a handle
        MemHandle handle = new MemHandle(10, 64, 15);

        // Check that start and size are set correctly
        assertEquals(10, handle.getStart());
        assertEquals(64, handle.getBlockSize());
    }


    // ----------------------------------------------------------
    /**
     * tests that setting multiple hands increases block size
     */
    public void testMultipleHandles()
    {
        MemHandle h1 = new MemHandle(0, 8, 5);
        MemHandle h2 = new MemHandle(8, 16, 5);

        assertEquals(0, h1.getStart());
        assertEquals(8, h1.getBlockSize());

        assertEquals(8, h2.getStart());
        assertEquals(16, h2.getBlockSize());
    }


    // ----------------------------------------------------------
    /**
     * tests that getRecord gives correct record size
     */
    public void testGetRecordSize()
    {
        MemHandle h1 = new MemHandle(0, 8, 5);
        MemHandle h2 = new MemHandle(8, 16, 5);

        assertEquals(5, h1.getRecordSize());
        assertEquals(8, h1.getBlockSize());

        assertEquals(5, h2.getRecordSize());
        assertEquals(16, h2.getBlockSize());
    }
}
