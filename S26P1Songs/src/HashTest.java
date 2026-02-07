import static org.junit.Assert.*;
import org.junit.Test;

public class HashTest {

	@Test
	public void singleInsertTest() {
		MemManager mm = new MemManager(2048);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle = hashTest.insert("Hunter");

		assertEquals(new MemHandle(0, 8, 5).getBlockSize(), testHandle.getBlockSize());
		assertEquals(new MemHandle(0, 8, 5).getStart(), testHandle.getStart());
	}

	@Test
	public void multipleInsertTest() {
		MemManager mm = new MemManager(2048);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle1 = hashTest.insert("Hunter");
		MemHandle testHandle2 = hashTest.insert("Hannah");

		assertEquals(new MemHandle(0, 8, 5).getBlockSize(), testHandle1.getBlockSize());
		assertEquals(new MemHandle(0, 8, 5).getStart(), testHandle1.getStart());
		assertEquals(new MemHandle(8, 8, 5).getBlockSize(), testHandle2.getBlockSize());
		assertEquals(new MemHandle(8, 8, 5).getStart(), testHandle2.getStart());
	}

	@Test
	public void getTest() {
		MemManager mm = new MemManager(32);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle1 = hashTest.insert("Hunter");

		assertEquals("Hunter", hashTest.get(testHandle1, m));
	}

	@Test
	public void colResTest() {
		MemManager mm = new MemManager(32);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle1 = hashTest.insert("Hunter");
		MemHandle testHandle2 = hashTest.insert("Hannah");
		MemHandle testHandle3 = hashTest.insert("Colton");
		MemHandle testHandle4 = hashTest.insert("Katlyn");

		assertEquals("Katlyn", hashTest.get(testHandle4, m));
		assertEquals("Colton", hashTest.get(testHandle3, m));
		assertEquals("Hannah", hashTest.get(testHandle2, m));
		assertEquals("Hunter", hashTest.get(testHandle1, m));
	}

	@Test
	public void dataNotFoundTest() {
		MemManager mm = new MemManager(32);
		int m = 10;
		Hash hashTest = new Hash(m, mm);
		Hash hashTest2 = new Hash(m, mm);

		MemHandle testHandle1 = hashTest.insert("Hunter");
		MemHandle testHandle2 = hashTest.insert("Hannah");
		MemHandle testHandle3 = hashTest.insert("Colton");
		MemHandle testHandle4 = hashTest2.insert("Katlyn");

		assertEquals("Data not found within hash table", hashTest.get(testHandle4, m));
	}

	@Test
	public void doubleHashSizeTest() {
		MemManager mm = new MemManager(32);
		int m = 4;
		Hash hashTest = new Hash(m, mm);
		assertEquals(4, hashTest.hashTable.length);

		MemHandle testHandle1 = hashTest.insert("Hunter");
		MemHandle testHandle2 = hashTest.insert("Hannah");
		MemHandle testHandle3 = hashTest.insert("Colton");
		assertEquals(8, hashTest.hashTable.length);

		MemHandle testHandle4 = hashTest.insert("Katlyn");
		MemHandle testHandle5 = hashTest.insert("Jaclyn");
		assertEquals(16, hashTest.hashTable.length);

	}

	@Test
	public void rehashTest() {
		MemManager mm = new MemManager(32);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle1 = hashTest.insert("Hunter");
		MemHandle testHandle2 = hashTest.insert("Hannah");
		MemHandle testHandle3 = hashTest.insert("Colton");
		MemHandle testHandle4 = hashTest.insert("Katlyn");

		hashTest.rehash();
	}

	@Test
	public void removeTest() {
		MemManager mm = new MemManager(32);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle1 = hashTest.insert("Hunter");
		assertTrue(hashTest.remove(testHandle1));
		assertFalse(hashTest.remove(testHandle1));

		testHandle1 = hashTest.insert("Hunter");
		MemHandle testHandle2 = hashTest.insert("Hannah");
		assertTrue(hashTest.remove(testHandle2));
		assertFalse(hashTest.remove(testHandle2));
		assertTrue(hashTest.remove(testHandle1));
		assertFalse(hashTest.remove(testHandle1));

	}
	
	@Test
	public void testContains() {
	    MemManager mm = new MemManager(64);
	    int m = 10;
	    Hash hashTest = new Hash(m, mm);

	    hashTest.insert("alpha");
	    hashTest.insert("beta");

	    assertTrue(hashTest.contains("alpha"));
	    assertTrue(hashTest.contains("beta"));

	    hashTest.remove("alpha");

	    assertFalse(hashTest.contains("alpha"));
	    assertTrue(hashTest.contains("beta"));
	}
	
	@Test
	public void testRehashData() {
	    MemManager mm = new MemManager(128);
	    int m = 4;
	    Hash hashTest = new Hash(m, mm);

	    hashTest.insert("A");
	    hashTest.insert("B");
	    hashTest.insert("C");

	    hashTest.rehash();

	    assertTrue(hashTest.contains("A"));
	    assertTrue(hashTest.contains("B"));
	    assertTrue(hashTest.contains("C"));
	}
	
	@Test
	public void testRemovetablePop() {
	    MemManager mm = new MemManager(128);
	    int m = 10;
	    Hash hashTest = new Hash(m, mm);

	    MemHandle h1 = hashTest.insert("one");
	    MemHandle h2 = hashTest.insert("two");

	    assertEquals("2", hashTest.printTable());

	    assertTrue(hashTest.remove(h1));
	    assertEquals("1", hashTest.printTable());

	    assertTrue(hashTest.remove("two") != null);
	    assertEquals("0", hashTest.printTable());
	}
	
	
}
