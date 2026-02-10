import static org.junit.Assert.*;
import org.junit.Test;
//
// public class HashTest
// {
//
// @Test
// public void singleInsertTest()
// {
// MemManager mm = new MemManager(2048);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
//
// MemHandle testHandle = hashTest.insert("Hunter");
//
// assertEquals(
// new MemHandle(0, 8, 5).getBlockSize(),
// testHandle.getBlockSize());
// assertEquals(new MemHandle(0, 8, 5).getStart(), testHandle.getStart());
// }
//
//
// @Test
// public void multipleInsertTest()
// {
// MemManager mm = new MemManager(2048);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
//
// MemHandle testHandle1 = hashTest.insert("Hunter");
// MemHandle testHandle2 = hashTest.insert("Hannah");
//
// assertEquals(
// new MemHandle(0, 8, 5).getBlockSize(),
// testHandle1.getBlockSize());
// assertEquals(new MemHandle(0, 8, 5).getStart(), testHandle1.getStart());
// assertEquals(
// new MemHandle(8, 8, 5).getBlockSize(),
// testHandle2.getBlockSize());
// assertEquals(new MemHandle(8, 8, 5).getStart(), testHandle2.getStart());
// }
//
//
// @Test
// public void getTest()
// {
// MemManager mm = new MemManager(32);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
//
// MemHandle testHandle1 = hashTest.insert("Hunter");
//
// assertEquals("Hunter", hashTest.get(testHandle1, m));
// }
//
//
// @Test
// public void colResTest()
// {
// MemManager mm = new MemManager(32);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
//
// MemHandle testHandle1 = hashTest.insert("Hunter");
// MemHandle testHandle2 = hashTest.insert("Hannah");
// MemHandle testHandle3 = hashTest.insert("Colton");
// MemHandle testHandle4 = hashTest.insert("Katlyn");
//
// assertEquals("Katlyn", hashTest.get(testHandle4, m));
// assertEquals("Colton", hashTest.get(testHandle3, m));
// assertEquals("Hannah", hashTest.get(testHandle2, m));
// assertEquals("Hunter", hashTest.get(testHandle1, m));
// }
//
//
// @Test
// public void dataNotFoundTest()
// {
// MemManager mm = new MemManager(32);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
// Hash hashTest2 = new Hash(m, mm);
//
// MemHandle testHandle1 = hashTest.insert("Hunter");
// MemHandle testHandle2 = hashTest.insert("Hannah");
// MemHandle testHandle3 = hashTest.insert("Colton");
// MemHandle testHandle4 = hashTest2.insert("Katlyn");
//
// assertEquals(
// "Data not found within hash table",
// hashTest.get(testHandle4, m));
// }
//
//
// @Test
// public void doubleHashSizeTest()
// {
// MemManager mm = new MemManager(32);
// int m = 4;
// Hash hashTest = new Hash(m, mm);
// assertEquals(4, hashTest.hashTable.length);
//
// MemHandle testHandle1 = hashTest.insert("Hunter");
// MemHandle testHandle2 = hashTest.insert("Hannah");
// MemHandle testHandle3 = hashTest.insert("Colton");
// assertEquals(8, hashTest.hashTable.length);
//
// MemHandle testHandle4 = hashTest.insert("Katlyn");
// MemHandle testHandle5 = hashTest.insert("Jaclyn");
// assertEquals(16, hashTest.hashTable.length);
//
// }
//
//
// @Test
// public void rehashTest()
// {
// MemManager mm = new MemManager(32);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
//
// MemHandle testHandle1 = hashTest.insert("Hunter");
// MemHandle testHandle2 = hashTest.insert("Hannah");
// MemHandle testHandle3 = hashTest.insert("Colton");
// MemHandle testHandle4 = hashTest.insert("Katlyn");
//
// hashTest.rehash();
// }
//
//
// @Test
// public void removeTest()
// {
// MemManager mm = new MemManager(32);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
//
// MemHandle testHandle1 = hashTest.insert("Hunter");
// assertTrue(hashTest.remove(testHandle1));
// assertFalse(hashTest.remove(testHandle1));
//
// testHandle1 = hashTest.insert("Hunter");
// MemHandle testHandle2 = hashTest.insert("Hannah");
// assertTrue(hashTest.remove(testHandle2));
// assertFalse(hashTest.remove(testHandle2));
// assertTrue(hashTest.remove(testHandle1));
// assertFalse(hashTest.remove(testHandle1));
//
// }
//
//
// @Test
// public void testContains()
// {
// MemManager mm = new MemManager(64);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
//
// hashTest.insert("alpha");
// hashTest.insert("beta");
//
// assertTrue(hashTest.contains("alpha"));
// assertTrue(hashTest.contains("beta"));
//
// hashTest.remove("alpha");
//
// assertFalse(hashTest.contains("alpha"));
// assertTrue(hashTest.contains("beta"));
// }
//
//
// @Test
// public void testRehashData()
// {
// MemManager mm = new MemManager(128);
// int m = 4;
// Hash hashTest = new Hash(m, mm);
//
// hashTest.insert("A");
// hashTest.insert("B");
// hashTest.insert("C");
//
// hashTest.rehash();
//
// assertTrue(hashTest.contains("A"));
// assertTrue(hashTest.contains("B"));
// assertTrue(hashTest.contains("C"));
// }
//
//
// @Test
// public void testRemovetablePop()
// {
// MemManager mm = new MemManager(128);
// int m = 10;
// Hash hashTest = new Hash(m, mm);
//
// MemHandle h1 = hashTest.insert("one");
// MemHandle h2 = hashTest.insert("two");
//
// assertEquals("2", hashTest.printTable());
//
// assertTrue(hashTest.remove(h1));
// assertEquals("1", hashTest.printTable());
//
// assertTrue(hashTest.remove("two") != null);
// assertEquals("0", hashTest.printTable());
// }
// }

public class HashTest
{

    @Test
    public void testHValues()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(16, mm);

        assertEquals(13, hash.h("Hunter", 16));
        assertEquals(9, hash.h("Hannah", 16));
        assertEquals(2, hash.h("Colton", 16));
        assertEquals(7, hash.h("Katelyn", 16));
    }


    @Test
    public void testHRangeAndBranches()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(8, mm);

        int idx = hash.h("ab", 8);
        assertTrue(idx >= 0 && idx < 8);

        int idx2 = hash.h("Hunter", 4);
        assertTrue(idx2 >= 0 && idx2 < 4);
    }


    @Test
    public void testInsertAndContains()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(2, mm);

        hash.insert("Hunter");
        hash.insert("Katelyn");

        assertTrue(hash.contains("Hunter"));
        assertTrue(hash.contains("Katelyn"));
        assertEquals("2", hash.printTable());
    }


    @Test
    public void testContainsMissing()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(10, mm);

        hash.insert("Hunter");
        assertFalse(hash.contains("Katelyn"));
    }


    @Test
    public void testQuadratic()
    {
        MemManager mm = new MemManager(2048);

        // Basic quadratic probing
        Hash hash1 = new Hash(8, mm);
        hash1.insert("AaAa");
        hash1.insert("BBBB");
        hash1.insert("CCCC");
        hash1.insert("DDDD");

        assertTrue(hash1.contains("AaAa"));
        assertTrue(hash1.contains("BBBB"));
        assertTrue(hash1.contains("CCCC"));
        assertTrue(hash1.contains("DDDD"));

        // Wrap-around probing (different table size)
        Hash hash2 = new Hash(5, new MemManager(2048));
        hash2.insert("AaAa");
        hash2.insert("BBBB");
        hash2.insert("CCCC");
        hash2.insert("DDDD");

        assertTrue(hash2.contains("DDDD"));
    }

    @Test
    public void testSkipTombstone()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(8, mm);

        hash.insert("AaAa");
        hash.insert("BBBB");
        hash.insert("CCCC");

        hash.remove("BBBB");

        assertFalse(hash.contains("BBBB"));
        assertTrue(hash.contains("AaAa"));
        assertTrue(hash.contains("CCCC"));
    }



    @Test
    public void testRemoveStringPop()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(10, mm);

        hash.insert("Hunter");
        hash.insert("Hannah");
        hash.insert("Colton");

        hash.remove("Hannah");

        assertEquals("2", hash.printTable());
        assertFalse(hash.contains("Hannah"));
    }


    @Test
    public void testRemoveHandlePop()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(8, mm);

        MemHandle h = hash.insert("Katelyn");

        assertEquals("1", hash.printTable());
        assertTrue(hash.remove(h));
        assertEquals("0", hash.printTable());
        assertFalse(hash.contains("Katelyn"));
    }


    @Test
    public void testRemoveDiff()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(8, mm);

        MemHandle h1 = hash.insert("Hunter");
        MemHandle copy =
            new MemHandle(h1.getStart(), h1.getBlockSize(), h1.getRecordSize());

        assertFalse(hash.remove(copy));
        assertTrue(hash.contains("Hunter"));

        assertTrue(hash.remove(h1));
        assertFalse(hash.contains("Hunter"));
    }


    @Test
    public void testGetHandle()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(8, mm);

        MemHandle h = hash.insert("Hunter");
        assertEquals("Hunter", hash.get(h, hash.hashTable.length));

        MemHandle fake = new MemHandle(999, 3, 3);
        assertEquals("Data not found within hash table", hash.get(fake, 8));
    }


    @Test
    public void testRehash()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(2, mm);

        hash.insert("Hunter");
        hash.insert("Hannah");
        hash.insert("Colton");
        hash.insert("Katelyn");

        assertTrue(hash.contains("Hunter"));
        assertTrue(hash.contains("Hannah"));
        assertTrue(hash.contains("Colton"));
        assertTrue(hash.contains("Katelyn"));
    }


    @Test
    public void testRehashNull()
    {
        MemManager mm = new MemManager(2048);
        Hash hash = new Hash(8, mm);

        MemHandle h1 = hash.insert("Hunter");
        hash.insert("Hannah");
        hash.insert("Colton");
        hash.insert("Katelyn");

        assertTrue(hash.remove(h1));

        hash.insert("Jaclyn");

        assertTrue(hash.contains("Hannah"));
        assertTrue(hash.contains("Colton"));
        assertTrue(hash.contains("Katelyn"));
        assertTrue(hash.contains("Jaclyn"));
        assertFalse(hash.contains("Hunter"));
    }
}
