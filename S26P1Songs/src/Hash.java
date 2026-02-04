import java.nio.charset.StandardCharsets;


/**
 * Implement a hash table.
 * Data: Strings
 * Hash function: sfold
 * Collision Resolution: Quadratic probing
 *
 * @author Hunter Dillon, Katelyn Cao
 * @version Milestone 1
 */


public class Hash
{

    /** Hash Table */
    MemHandle[] hashTable;
    /** Memory Manager */
    MemManager mm;
    
    /**
     * Create a new Hash object.
     *
     * @param init
     *            Initial size for table
     * @param m
     *            Memory manager used by this table to store objects
     */
    public Hash(int init, MemManager m)
    {
        hashTable = new MemHandle[init];
        mm = m;
    }

    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    public int h(String s, int m) {
        long sum = 0;
        long mult = 1;
        for (int i = 0; i < s.length(); i++) {
            mult = (i % 4 == 0) ? 1 : mult * 256;
            sum += s.charAt(i) * mult;
        }
        return (int)(Math.abs(sum) % m);
    }
    
    
    public String get(MemHandle handle, int m)
    {
        //Transfer handle to initial index
        byte[] byteArray = mm.getRecord(handle);
        String stringData = new String(byteArray, StandardCharsets.ISO_8859_1);
        int initialIndex = h(stringData, m);
        int i = initialIndex;
        
        while (hashTable[i] != null)
        {
            if (hashTable[i] == handle)
            {
                return stringData;
            }
            
            //If not in the first location, iterate through the conflict 
            //  resolutions until found
            else
            {
                i = colRes(i);
            } 
        }
        return "Data Not Found In Hash Table";
    }
    
    public MemHandle insert(String data, int m)
    {
        //Calculating the hash function and creating a handle placeholder
        int initialIndex = h(data, m);
        MemHandle handle = null;
        
        
        boolean allocated = false;
        while (!allocated)
        {
            //Checking if the handle is in the first location in the table (need to account for tombstones)
            if (hashTable[initialIndex] == null)
            {
                //insert into memory manager, place the handle in the table, and declare found
                handle = mm.insert(data.getBytes());
                hashTable[initialIndex] = handle;
                allocated = true;
            }
            //If not in the first location, iterate through the conflict resolutions until found
            else
            {
                //Iterating until an empty location is found
                int i = initialIndex;
                while (hashTable[i] != null)
                {
                    i = colRes(i);
                }
                
                handle = mm.insert(data.getBytes());
                hashTable[i] = handle;
                allocated = true;
            }
        }
        return handle;
    }
    
    
    
    
    
    private int colRes(int homeSlot)
    {
        int i = homeSlot;
        int j = 0;
        while (hashTable[i] != null)
        {
            i = homeSlot + 2 ^ j;
            j++;
        }
        return i;
    }
}
