import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MemManagerTest {

    /** Memory manager getting tested on */
    MemManager mm;

    @Before
    public void setUp() {
        mm = new MemManager(16); // Initial pool size 16 bytes
    }

    // -------------------------
    // 1. Constructor / Initialization
    // -------------------------
    @Test
    public void testConstructor() {
        assertEquals(16, mm.poolSize);
        assertNotNull(mm.memoryPool);
        assertEquals(5, mm.freeLists.length); // indices 0..4
        assertFalse(mm.freeLists[4].isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidZero() {
    new MemManager(0);
}

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidNonPowerOfTwo() {
        new MemManager(10);
    }

    // -------------------------
    // 2. Simple Allocation
    // -------------------------
    @Test
    public void testSimpleInsert() {
        byte[] data = new byte[4];
        MemHandle h = mm.insert(data);
        assertEquals(4, h.getBlockSize());
        assertTrue(h.getStart() >= 0 && h.getStart() < mm.poolSize);
        // Check free lists updated (block of size 8 should be split)
        assertFalse(mm.freeLists[2].isEmpty()); // buddy of 4 bytes
    }

    // -------------------------
    // 3. Multiple Allocations
    // -------------------------
    @Test
    public void testMultipleInserts() {
        byte[] d1 = new byte[4];
        byte[] d2 = new byte[4];
        MemHandle h1 = mm.insert(d1);
        MemHandle h2 = mm.insert(d2);
        assertNotEquals(h1.getStart(), h2.getStart());
    }

    // -------------------------
    // 4. Pool Growth
    // -------------------------
    @Test
    public void testPoolGrowth() {
        // Force allocation bigger than initial pool (16)
        byte[] data = new byte[20];
        MemHandle h = mm.insert(data);
        assertEquals(32, mm.poolSize); // pool doubled
        assertEquals(32, h.getBlockSize());
        assertTrue(h.getStart() < 32);
    }
    
    // -------------------------
    // 4. Multiple Pool Growths
    // -------------------------
    @Test
    public void testMultiplePoolGrowths() {
        MemHandle h1 = mm.insert(new byte[20]); // grows to 32
        MemHandle h2 = mm.insert(new byte[20]); // grows to 64

        assertEquals(64, mm.poolSize);
        assertEquals(6, mm.freeLists.length - 1); // maxK updated
        assertTrue(h2.getStart() < mm.poolSize);
    }

    // -------------------------
    // 5. Release No Buddy Merge
    // -------------------------
    @Test
    public void testReleaseNoBuddy() {
        byte[] data = new byte[4];
        MemHandle h1 = mm.insert(data);
        MemHandle h2 = mm.insert(data);
        mm.release(h1);
        // Check that the free list for size 4 now contains the block again
        assertTrue(mm.freeLists[0].isEmpty());
        assertTrue(mm.freeLists[1].isEmpty());
        assertFalse(mm.freeLists[2].isEmpty());
        assertFalse(mm.freeLists[3].isEmpty());
        assertTrue(mm.freeLists[4].isEmpty());
    }

    // -------------------------
    // 5. Release With Buddy Merge from 4 -> 32
    // -------------------------
    @Test
    public void testReleaseWithBuddyMerge() {
        // Allocate two adjacent blocks
        MemHandle h1 = mm.insert(new byte[4]);
        MemHandle h2 = mm.insert(new byte[4]);
        // Release both, should merge back into size 8 block
        mm.release(h1);
        mm.release(h2);
        // Check free list for 8 bytes contains the merged block
        assertFalse(mm.freeLists[4].isEmpty());
        for (int i = 0; i < 4; i++)
        {
            assertTrue(mm.freeLists[i].isEmpty());
        }
        
    }

    // -------------------------
    // 5. Further RecursiveReleaseMerging
    // -------------------------
    @Test
    public void testRecursiveReleaseMerge() {
        MemHandle h1 = mm.insert(new byte[4]);
        MemHandle h2 = mm.insert(new byte[4]);
        MemHandle h3 = mm.insert(new byte[8]);

        mm.release(h1);
        mm.release(h2); // merges to 8
        mm.release(h3); // merges to 16

        assertFalse(mm.freeLists[4].isEmpty()); // full block restored
    }
    
    // -------------------------
    // 6. Get Record
    // -------------------------
    @Test
    public void testGetRecord() {
        byte[] data = {1, 2, 3, 4};
        MemHandle h = mm.insert(data);
        // Copy data into memoryPool manually
        for (int i = 0; i < data.length; i++)
        {
            mm.memoryPool[h.getStart() + i] = data[i];
        }
        byte[] retrieved = mm.getRecord(h);
        assertArrayEquals(data, retrieved);
    }

    // -------------------------
    // 7. Edge Case: allocate all memory and free
    // -------------------------
    @Test
    public void testAllocateAllAndFree() {
        MemHandle h = mm.insert(new byte[16]);
        assertEquals(16, h.getBlockSize());
        mm.release(h);
        assertFalse(mm.freeLists[4].isEmpty()); // back to single full block
    }

    // -------------------------
    // 8. Stress Test
    // -------------------------
    @Test
    public void testRepeatedAllocRelease() {
        for (int i = 0; i < 10; i++)
        {
            MemHandle h = mm.insert(new byte[4]);
            mm.release(h);
        }
        assertFalse(mm.freeLists[4].isEmpty()); // largest block restored
    }
}
