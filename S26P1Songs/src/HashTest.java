import static org.junit.Assert.*;
import org.junit.Test;

public class HashTest {

	@Test
	public void singleInsertTest() {
		MemManager mm = new MemManager(2048);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle = hashTest.insert("Hunter", m);

		assertEquals(new MemHandle(0, 8, 5).getBlockSize(), testHandle.getBlockSize());
		assertEquals(new MemHandle(0, 8, 5).getStart(), testHandle.getStart());
	}

	@Test
	public void multipleInsertTest() {
		MemManager mm = new MemManager(2048);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle1 = hashTest.insert("Hunter", m);
		MemHandle testHandle2 = hashTest.insert("Hannah", m);

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

		MemHandle testHandle1 = hashTest.insert("Hunter", m);

		assertEquals("Hunter", hashTest.get(testHandle1, m));
	}
	
	@Test
	public void colResTest() {
		MemManager mm = new MemManager(32);
		int m = 10;
		Hash hashTest = new Hash(m, mm);

		MemHandle testHandle1 = hashTest.insert("Hunter", m);
		MemHandle testHandle2 = hashTest.insert("Hannah", m);
		MemHandle testHandle3 = hashTest.insert("Colton", m);
		MemHandle testHandle4 = hashTest.insert("Katlyn", m);

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

		MemHandle testHandle1 = hashTest.insert("Hunter", m);
		MemHandle testHandle2 = hashTest.insert("Hannah", m);
		MemHandle testHandle3 = hashTest.insert("Colton", m);
		MemHandle testHandle4 = hashTest2.insert("Katlyn", m);

		assertEquals("Data Not Found In Hash Table", hashTest.get(testHandle4, m));
	}
	
	@Test
	public void doubleHashSizeTest() {
		MemManager mm = new MemManager(32);
		int m = 4;
		Hash hashTest = new Hash(m, mm);
		assertEquals(4, hashTest.hashTable.length);
		
		MemHandle testHandle1 = hashTest.insert("Hunter", m);
		MemHandle testHandle2 = hashTest.insert("Hannah", m);
		MemHandle testHandle3 = hashTest.insert("Colton", m);
		assertEquals(8, hashTest.hashTable.length);
		
		MemHandle testHandle4 = hashTest.insert("Katlyn", m);
		MemHandle testHandle5 = hashTest.insert("Jaclyn", m);
		assertEquals(16, hashTest.hashTable.length);

	}
	
	@Test
	public void rehashTest() {
		MemManager mm = new MemManager(32);
		int m = 10;
		Hash hashTest = new Hash(m, mm);
		
		MemHandle testHandle1 = hashTest.insert("Hunter", m);
		MemHandle testHandle2 = hashTest.insert("Hannah", m);
		MemHandle testHandle3 = hashTest.insert("Colton", m);
		MemHandle testHandle4 = hashTest.insert("Katlyn", m);
		
		hashTest.rehash();
	}
	
	@Test
	public void removeTest() {
		MemManager mm = new MemManager(32);
		int m = 10;
		Hash hashTest = new Hash(m, mm);
		
		MemHandle testHandle1 = hashTest.insert("Hunter", m);
		assertTrue(hashTest.remove(testHandle1));
		assertFalse(hashTest.remove(testHandle1));
		
		testHandle1 = hashTest.insert("Hunter", m);
		MemHandle testHandle2 = hashTest.insert("Hannah", m);
		assertTrue(hashTest.remove(testHandle2));
		assertFalse(hashTest.remove(testHandle2));
		assertTrue(hashTest.remove(testHandle1));
		assertFalse(hashTest.remove(testHandle1));

	}
}
