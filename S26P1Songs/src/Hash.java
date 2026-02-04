import java.nio.charset.StandardCharsets;

/**
 * Implement a hash table. Data: Strings Hash function: sfold Collision
 * Resolution: Quadratic probing
 *
 * @author Hunter Dillon, Katelyn Cao
 * @version Milestone 1
 */

public class Hash {

	/** Tombstone stand-in */
	private static final MemHandle TOMBSTONE = new MemHandle(-1, -1, -1);
	
	/** Hash Table */
	MemHandle[] hashTable;
	/** Memory Manager */
	MemManager mm;
	/** Table size */
	int m;
	/** Table population */
	int tablePop;

	/**
	 * Create a new Hash object.
	 *
	 * @param init Initial size for table
	 * @param m    Memory manager used by this table to store objects
	 */
	public Hash(int init, MemManager mem) {
		hashTable = new MemHandle[init];
		mm = mem;
		m = init;
	}

	/**
	 * Compute the hash function. Uses the "sfold" method from the OpenDSA module on
	 * hash functions
	 *
	 * @param s The string that we are hashing
	 * @param m The size of the hash table
	 * @return The home slot for that string
	 */
	public int h(String s, int m) {
		long sum = 0;
		long mult = 1;
		for (int i = 0; i < s.length(); i++) {
			mult = (i % 4 == 0) ? 1 : mult * 256;
			sum += s.charAt(i) * mult;
		}
		return (int) (Math.abs(sum) % m);
	}

	public String get(MemHandle handle, int m) {
		// Transfer handle to initial index
		byte[] byteArray = mm.getRecord(handle);
		String stringData = new String(byteArray, 0, handle.getRecordSize(), StandardCharsets.ISO_8859_1);
		int initialIndex = h(stringData, m);
		int i = initialIndex;
		int j = 0;

		//steps through the quadratic probing, checking if null or the correct handle
	    while (true) {
	        i = colResStep(initialIndex, j);

	        if (hashTable[i] == null) {
	            return "Data Not Found In Hash Table";
	        }

	        if (hashTable[i] == handle) {
	            return stringData;
	        }

	        j++;
	    }
	}

	public MemHandle insert(String data, int m) {
		// Calculating the hash function and creating a handle placeholder
		int initialIndex = h(data, m);
		MemHandle handle = null;
		
		if (tablePop + 1 > hashTable.length / 2)
		{
			this.doubleHashSize();
			this.rehash();
		}
		
		boolean allocated = false;
		while (!allocated) {
			// Checking if the handle is in the first location in the table (need to account
			// for tombstones)
			if (hashTable[initialIndex] == null || hashTable[initialIndex] == TOMBSTONE) {
				// insert into memory manager, place the handle in the table, and declare found
				handle = mm.insert(data.getBytes());
				hashTable[initialIndex] = handle;
				tablePop++;
				allocated = true;
			}
			// If not in the first location, iterate through the conflict resolutions until
			// found
			else {
				// Iterating until an empty location is found
				int i = initialIndex;
				while (hashTable[i] != null || hashTable[i] == TOMBSTONE) {
					i = colRes(i);
				}

				handle = mm.insert(data.getBytes());
				hashTable[i] = handle;
				tablePop++;
				allocated = true;
			}
		}
		return handle;
	}

	
	public boolean remove(MemHandle handle) {
	    int j = 0;
	    byte[] bytes = mm.getRecord(handle);
	    String key = new String(bytes, 0, handle.getRecordSize(),
	        StandardCharsets.ISO_8859_1);

	    int home = h(key, m);

	    while (true) {
	        int i = (home + j * j) % hashTable.length;

	        if (hashTable[i] == null) {
	            return false; // not found
	        }

	        if (hashTable[i] == handle) {
	            hashTable[i] = TOMBSTONE;
	            tablePop--;
	            return true;
	        }
	        j++;
	    }
	}
	
	
	
	
	private int colRes(int homeSlot) {
	    int j = 0;
	    int i;
	    while (true) {
	        i = colResStep(homeSlot, j);

	        //wrap around table
	        i = i % hashTable.length;

	        if (hashTable[i] == null || hashTable[i] == TOMBSTONE) {
	            return i;
	        }

	        j++;
	    }
	}
	
	private int colResStep(int homeSlot, int j) {
	    return homeSlot + j * j;
	}
	
	public void doubleHashSize()
	{
		m = this.hashTable.length * 2;
		MemHandle[] newHashTable = new MemHandle[m];
		
		System.arraycopy(hashTable, 0, newHashTable, 0, hashTable.length);
		hashTable = newHashTable;
	}
	
	public void rehash()
	{
		//Creating a new table to be populated
		MemHandle[] currHashTableCopy = hashTable;
		hashTable = new MemHandle[currHashTableCopy.length];
		tablePop = 0;
		
		//Going through all the items in the old hash table
		for (int i = 0; i < this.hashTable.length; i++)
		{
			if (currHashTableCopy[i] != null)
			{
				MemHandle handle = currHashTableCopy[i];
				byte[] byteArray =  mm.getRecord(handle);
				String value = new String(byteArray, 0, handle.getRecordSize(), StandardCharsets.ISO_8859_1);

				this.insert(value, m);
			}
		}
	}
	
	public MemHandle remove(String data) {
	    // TODO real implementation later
	    return null;
	}
	
	public String printTable(String kind) {
	    // TODO real implementation later
	    return "";
	}
}
