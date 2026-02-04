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
}
