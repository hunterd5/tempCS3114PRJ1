import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 * A single linked list holding free blocks within the memory manager
 *
 * @author Katelyn Cao, Hunter Dillon
 * @version feb 6, 2026
 */

public class FreeBlockListTest
{

    private FreeBlock testBlock1;
    private FreeBlock testBlock2;
    private FreeBlock testBlock3;
    private FreeBlockList blockList;

    @Before
    public void setUp()
    {
        testBlock1 = new FreeBlock(0, 8);
        testBlock2 = new FreeBlock(8, 8);
        testBlock3 = new FreeBlock(16, 8);
        blockList = new FreeBlockList();
    }


    @Test
    // ----------------------------------------------------------
    /**
     * tests that empty returns true when empty and false when not
     */
    public void emptyTest()
    {
        // initially empty
        assertTrue(blockList.isEmpty());

        // null when empty
        assertNull(blockList.getFirst());
        assertNull(blockList.pop());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that add method successfully adds new block
     */
    public void addTest()
    {
        blockList.add(testBlock1);

        // list not empty after add
        assertFalse(blockList.isEmpty());
        // added to the head
        assertSame(testBlock1, blockList.getFirst());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that add method successfully adds on the head
     */
    public void multipleAddTest()
    {
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);

        // latest add is the head
        assertSame(testBlock3, blockList.getFirst());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that pop method successfully removes the head block
     */
    public void popTest()
    {
        assertNull(blockList.pop());

        // pop one block
        blockList.add(testBlock1);
        FreeBlock popped = blockList.pop();
        assertSame(testBlock1, popped);
        assertTrue(blockList.isEmpty());

        // pop multiple blocks
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        assertSame(testBlock3, blockList.pop());
        assertSame(testBlock2, blockList.pop());
        assertSame(testBlock1, blockList.pop());
        assertNull(blockList.pop());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that remove method successfully removes the block and moves next
     * block to head if applicable
     */
    public void emptyRemoveTest()
    {
        // remove from empty list
        blockList.remove(testBlock1);
        assertTrue(blockList.isEmpty());

        // removing single block head
        blockList.add(testBlock1);
        blockList.remove(testBlock1);
        assertTrue(blockList.isEmpty());

        // removing block chain head
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.remove(testBlock2);
        assertSame(testBlock1, blockList.getFirst());

    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that remove method works from any position and a nonexistent block
     */
    public void testRemove()
    {

        // removing from the middle
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock2);
        assertSame(testBlock3, blockList.pop());
        assertSame(testBlock1, blockList.pop());
        assertNull(blockList.pop());

        // removing from the end
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock1);
        assertSame(testBlock3, blockList.pop());
        assertSame(testBlock2, blockList.pop());
        assertNull(blockList.pop());

        // removing missing block
        FreeBlock testBlock4 = new FreeBlock(32, 8);
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.remove(testBlock4);
        assertSame(testBlock2, blockList.pop());
        assertSame(testBlock1, blockList.pop());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that blocks are adjusted properly after remove
     */
    public void multipleRemoveTest()
    {
        // remove everything
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock2);
        blockList.remove(testBlock3);
        blockList.remove(testBlock1);
        assertNull(blockList.pop());

        // removing last 2
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock2);
        blockList.remove(testBlock3);
        assertSame(testBlock1, blockList.pop());
        assertNull(blockList.pop());

        // Removing front 2
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock2);
        blockList.remove(testBlock1);
        assertSame(testBlock3, blockList.pop());
        assertNull(blockList.pop());

        // removing both front and back
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock3);
        blockList.remove(testBlock1);
        assertSame(testBlock2, blockList.pop());
        assertNull(blockList.pop());
        assertTrue(blockList.isEmpty());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * test the geFirst method returns correct block
     */
    public void getFirstTest()
    {
        blockList.add(testBlock1);
        blockList.add(testBlock2);

        // test that block2 is the head
        assertSame(testBlock2, blockList.getFirst());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that remove only works with blocks not values
     */
    public void removeEqualityTest()
    {
        // Creates 2 identical blocks to check if a block with the same data
        // will remove the one in the list
        FreeBlock b1a = new FreeBlock(0, 8);
        FreeBlock b1b = new FreeBlock(0, 8);
        blockList.add(b1a);
        blockList.remove(b1b);
        assertSame(b1a, blockList.getFirst());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that finBuddy returns the correct FreeBlock
     */
    public void findBuddyTest()
    {
        blockList.add(testBlock1);
        blockList.add(testBlock2);

        // create buddy and check same
        FreeBlock buddy = blockList.findBuddy(testBlock1.start);
        assertSame(testBlock1, buddy);

        // remove block1
        blockList.remove(testBlock1);

        // cant fine because of remove
        FreeBlock buddy2 = blockList.findBuddy(testBlock1.start);
        assertEquals(null, buddy2);
    }

    @Test
    // ----------------------------------------------------------
    /**
     * tests that getStart returns the correct string output
     */
    public void getStartsTest()
    {
        // empty
        assertEquals("", blockList.getStarts());

        // one
        blockList.add(testBlock1);
        assertEquals("0 ", blockList.getStarts());

        // two
        blockList.add(testBlock2);
        assertEquals("8 0 ", blockList.getStarts());

        // three
        blockList.add(testBlock3);
        assertEquals("16 8 0 ", blockList.getStarts());

        // remove from middle
        blockList.remove(testBlock2);
        assertEquals("16 0 ", blockList.getStarts());
    }

    @Test
    // ----------------------------------------------------------
    /**
     * test that getStart gets sorted in ascending order with no trailing for
     * correct printing
     */
    public void testGetStartsSorted()
    {
        // empty
        assertEquals("", blockList.getStartsSorted());

        // one
        blockList.add(testBlock1); // start 0
        assertEquals("0", blockList.getStartsSorted());

        // two
        blockList.add(testBlock2); // start 8
        assertEquals("0 8", blockList.getStartsSorted());

        // three
        blockList.add(testBlock3); // start 16
        assertEquals("0 8 16", blockList.getStartsSorted());

        // remove middle
        blockList.remove(testBlock2);
        assertEquals("0 16", blockList.getStartsSorted());
    }
}
