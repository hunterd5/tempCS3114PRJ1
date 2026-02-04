import static org.junit.Assert.*;
import org.junit.Test;

public class MemHandleTest {

    @Test
    public void testConstructorAndGetters() {
        // Create a handle
        MemHandle handle = new MemHandle(10, 64);

        // Check that start and size are set correctly
        assertEquals(10, handle.getStart());
        assertEquals(64, handle.getSize());
    }

    @Test
    public void testMultipleHandles() {
        MemHandle h1 = new MemHandle(0, 8);
        MemHandle h2 = new MemHandle(8, 16);

        assertEquals(0, h1.getStart());
        assertEquals(8, h1.getSize());

        assertEquals(8, h2.getStart());
        assertEquals(16, h2.getSize());
    }
}
