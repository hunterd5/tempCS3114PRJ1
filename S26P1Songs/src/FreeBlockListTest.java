import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class FreeBlockListTest {

    private FreeBlock testBlock1;
    private FreeBlock testBlock2;
    private FreeBlock testBlock3;
    private FreeBlockList blockList;
    
    @Before
    public void setUp() {
        testBlock1 = new FreeBlock(0, 8);
        testBlock2 = new FreeBlock(8, 8);
        testBlock3 = new FreeBlock(16, 8);
        blockList = new FreeBlockList();
    }
    
    @Test
    public void emptyTest() {
        assertTrue(blockList.isEmpty());
        assertNull(blockList.getFirst());
        assertNull(blockList.pop());
    }
    
    @Test
    public void addTest() {
        blockList.add(testBlock1);
        assertFalse(blockList.isEmpty());
        assertSame(testBlock1, blockList.getFirst());
    }
    
    @Test
    public void multipleAddTest() {
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        assertSame(testBlock3, blockList.getFirst());
    }
    
    @Test
    public void PopTest() {
        //Empty list
        assertNull(blockList.pop());
        
        //One element
        blockList.add(testBlock1);
        FreeBlock popped = blockList.pop();
        assertSame(testBlock1, popped);
        assertTrue(blockList.isEmpty());
        
        //Multiple elements
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        assertSame(testBlock3, blockList.pop());
        assertSame(testBlock2, blockList.pop());
        assertSame(testBlock1, blockList.pop());
        assertNull(blockList.pop());
    }
    
    @Test
    public void emptyRemoveTest() {
        //Testing removing from empty list
        blockList.remove(testBlock1);
        assertTrue(blockList.isEmpty());
        
        //Removing head element when it is alone
        blockList.add(testBlock1);
        blockList.remove(testBlock1);
        assertTrue(blockList.isEmpty());
        
        //Removing head element with multiple elements
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.remove(testBlock2);
        assertSame(testBlock1, blockList.getFirst());
        
    }
    
    @Test
    public void elementalRemoveTest() {
    	//Removing middle element
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock2);
        assertSame(testBlock3, blockList.pop());
        assertSame(testBlock1, blockList.pop());
        assertNull(blockList.pop());
        
        //Removing end element
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock1);
        assertSame(testBlock3, blockList.pop());
        assertSame(testBlock2, blockList.pop());
        assertNull(blockList.pop());
       
        //Removing non-existent block
        FreeBlock testBlock4 = new FreeBlock(32, 8);
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.remove(testBlock4);
        assertSame(testBlock2, blockList.pop());
        assertSame(testBlock1, blockList.pop());
    }
    
    @Test
    public void multipleRemoveTest() {
    	//Removing all elements element
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock2);
        blockList.remove(testBlock3);
        blockList.remove(testBlock1);
        assertNull(blockList.pop());
        
        //Removing back 2 elements
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock2);
        blockList.remove(testBlock3);
        assertSame(testBlock1, blockList.pop());
        assertNull(blockList.pop());
       
        //Removing front 2 elements
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        blockList.add(testBlock3);
        blockList.remove(testBlock2);
        blockList.remove(testBlock1);
        assertSame(testBlock3, blockList.pop());
        assertNull(blockList.pop());
        
        //Removing front and back elements
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
    public void getFirstTest()
    {
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        assertSame(testBlock2, blockList.getFirst());
        assertSame(testBlock2, blockList.getFirst());
    }
    
    @Test
    public void removeEqualityTest()
    {
        //Creates 2 identical blocks to check if a block with the same data will remove the one in the list
        FreeBlock b1a = new FreeBlock(0, 8);
        FreeBlock b1b = new FreeBlock(0, 8);
        blockList.add(b1a);
        blockList.remove(b1b);
        assertSame(b1a, blockList.getFirst());
    }
    
    @Test
    public void findBuddyTest()
    {
        blockList.add(testBlock1);
        blockList.add(testBlock2);
        FreeBlock buddy = blockList.findBuddy(testBlock1.start);
        assertSame(testBlock1, buddy);
        
        blockList.remove(testBlock1);
        FreeBlock buddy2 = blockList.findBuddy(testBlock1.start);
        assertEquals(null, buddy2);
    }
    
    @Test
    public void getStartsTest()
    {
    	assertEquals("", blockList.getStarts());
    	
    	blockList.add(testBlock1);
        assertEquals("0 ", blockList.getStarts());
        
        blockList.add(testBlock2);
        assertEquals("8 0 ", blockList.getStarts());
        
        blockList.add(testBlock3);
        assertEquals("16 8 0 ", blockList.getStarts());
        
        blockList.remove(testBlock2);
        assertEquals("16 0 ", blockList.getStarts());
    }
}
