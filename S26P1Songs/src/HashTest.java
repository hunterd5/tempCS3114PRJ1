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
	
//	@Test
//	public void testTablePopDecrementsOnRemoveHandle() {
//	    MemManager mm = new MemManager(128);
//	    int m = 10;
//	    Hash hashTest = new Hash(m, mm);
//
//	    MemHandle h1 = hashTest.insert("one");
//	    MemHandle h2 = hashTest.insert("two");
//	    MemHandle h3 = hashTest.insert("three");
//
//	    assertEquals("3", hashTest.printTable());
//
//	    assertTrue(hashTest.remove(h2));
//	    assertEquals("2", hashTest.printTable());
//
//	    assertTrue(hashTest.remove(h1));
//	    assertEquals("1", hashTest.printTable());
//
//	    assertTrue(hashTest.remove(h3));
//	    assertEquals("0", hashTest.printTable());
//	}
//	
//	@Test
//	public void testHashMultLine51() {
//	    MemManager mm = new MemManager(128);
//	    int m = 10;
//	    Hash hashTest = new Hash(m, mm);
//
//	    String s = "abcde"; // hits i=0 (mult=1), i=1..3 (*256 chain), i=4 reset to 1
//
//	    long sum = 0;
//	    long mult = 1;
//	    for (int i = 0; i < s.length(); i++) {
//	        if (i % 4 == 0) {
//	            mult = 1;
//	        }
//	        else {
//	            mult = mult * 256;
//	        }
//	        sum += s.charAt(i) * mult;
//	    }
//
//	    int expected = (int)(Math.abs(sum) % m);
//	    assertEquals(expected, hashTest.h(s, m));
//	}
//	
//	@Test
//	public void testGetColResHandleLoopLine237() {
//	    MemManager mm = new MemManager(256);
//	    int m = 10;
//	    Hash hashTest = new Hash(m, mm);
//
//	    String s1 = null;
//	    String s2 = null;
//
//	    for (char a = 'a'; a <= 'z' && s2 == null; a++) {
//	        for (char b = 'a'; b <= 'z' && s2 == null; b++) {
//	            String first = "" + a + b;
//
//	            for (char c = 'a'; c <= 'z' && s2 == null; c++) {
//	                for (char d = 'a'; d <= 'z' && s2 == null; d++) {
//	                    String second = "" + c + d;
//
//	                    if (!first.equals(second)
//	                        && hashTest.h(first, m) == hashTest.h(second, m)) {
//	                        s1 = first;
//	                        s2 = second;
//	                    }
//	                }
//	            }
//	        }
//	    }
//
//	    assertNotNull(s1);
//	    assertNotNull(s2);
//
//	    MemHandle h1 = hashTest.insert(s1);
//	    MemHandle h2 = hashTest.insert(s2);
//
//	    assertEquals(s1, hashTest.get(h1, m));
//	    assertEquals(s2, hashTest.get(h2, m));
//	}
//	
//	@Test
//	public void testGetColResStringLoopLine273() {
//	    MemManager mm = new MemManager(256);
//	    int m = 10;
//	    Hash hashTest = new Hash(m, mm);
//
//	    String s1 = null;
//	    String s2 = null;
//
//	    for (char a = 'a'; a <= 'z' && s2 == null; a++) {
//	        for (char b = 'a'; b <= 'z' && s2 == null; b++) {
//	            String first = "" + a + b;
//
//	            for (char c = 'a'; c <= 'z' && s2 == null; c++) {
//	                for (char d = 'a'; d <= 'z' && s2 == null; d++) {
//	                    String second = "" + c + d;
//
//	                    if (!first.equals(second)
//	                        && hashTest.h(first, m) == hashTest.h(second, m)) {
//	                        s1 = first;
//	                        s2 = second;
//	                    }
//	                }
//	            }
//	        }
//	    }
//
//	    assertNotNull(s1);
//	    assertNotNull(s2);
//
//	    hashTest.insert(s1);
//	    hashTest.insert(s2);
//
//	    assertNotNull(hashTest.remove(s2));
//	    assertFalse(hashTest.contains(s2));
//	    assertTrue(hashTest.contains(s1));
//	}
//	
//	@Test
//	public void testInsertReusesTombstoneLine325() {
//	    MemManager mm = new MemManager(256);
//	    int m = 10;
//	    Hash hashTest = new Hash(m, mm);
//
//	    // Find three strings with the SAME home slot:
//	    // insert s1, s2 so s2 goes to a probe spot,
//	    // remove s1 to create a tombstone at the home slot,
//	    // then insert s3 which should reuse that tombstone.
//	    String s1 = null;
//	    String s2 = null;
//	    String s3 = null;
//
//	    for (char a = 'a'; a <= 'z' && s3 == null; a++) {
//	        for (char b = 'a'; b <= 'z' && s3 == null; b++) {
//	            String first = "" + a + b;
//
//	            for (char c = 'a'; c <= 'z' && s3 == null; c++) {
//	                for (char d = 'a'; d <= 'z' && s3 == null; d++) {
//	                    String second = "" + c + d;
//
//	                    if (!first.equals(second)
//	                        && hashTest.h(first, m) == hashTest.h(second, m)) {
//
//	                        for (char e = 'a'; e <= 'z' && s3 == null; e++) {
//	                            for (char f = 'a'; f <= 'z' && s3 == null; f++) {
//	                                String third = "" + e + f;
//	                                if (!third.equals(first)
//	                                    && !third.equals(second)
//	                                    && hashTest.h(third, m) == hashTest.h(first, m)) {
//	                                    s1 = first;
//	                                    s2 = second;
//	                                    s3 = third;
//	                                }
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	    }
//
//	    assertNotNull(s1);
//	    assertNotNull(s2);
//	    assertNotNull(s3);
//
//	    MemHandle h1 = hashTest.insert(s1);
//	    hashTest.insert(s2);
//
//	    int home = hashTest.h(s1, m);
//
//	    // s1 should be sitting at its home slot (very likely; and if not, this test will fail fast)
//	    assertTrue(hashTest.hashTable[home] == h1);
//
//	    assertTrue(hashTest.remove(h1));
//	    assertTrue(hashTest.hashTable[home] != null); // should now be TOMBSTONE
//
//	    MemHandle h3 = hashTest.insert(s3);
//
//	    // New insert should reuse the tombstone at home slot
//	    assertTrue(hashTest.hashTable[home] == h3);
//	    assertEquals(s3, hashTest.get(h3, m));
//	}
	
}
