
/**
 * Memory Manager class.
 * This version uses an array in memory.
 * This version implements the buddy method.
 *
 * @author Hunter Dillon, Katelyn Cao
 * @version Milestone 1
 */

public class MemManager {
    
    //Fields
    /**Raw byte array */
    byte[] memoryPool;
    /**total bytes in array */
    int poolSize;
    /**one freeList per block size with each index representing a block size 
     * of 2^k */
    FreeBlockList[] freeLists;
    /**The smallest k that will be greater than or equal to M give that M is a 
     * power of 2 */
    int maxK;
    
    
    
    
     /**
     * Create a new MemManager object.
     *
     * @param startSize
     *            Initial size of the memory pool
     */
    public MemManager(int startSize) {
        //Check to see if start size(M) is compatible if it is a power of 2 
        //  and > 0
        if (startSize <= 0 || !isPowerOfTwo(startSize))
        {
            throw new IllegalArgumentException("Memory pool size must be "
                + "greater than 0 and a power of 2");
        }
      
        //Raw byte array
        memoryPool = new byte[startSize];
        //total bytes in array
        poolSize = startSize;
        //Finding the the smallest k that will be greater than or equal to M 
        //  give that M is a power of 2
        maxK = log2(poolSize);
        
        
        //Initializing freeLists
    
        
        //Creating an array of FreeBlockLists to store all the available blocks
        //Each index represents a selection of blocks of 2^i size
        freeLists = new FreeBlockList[maxK + 1];
        
        //Populating all the slots within the freeLists array with a new 
        //  FreeBlockList
        for (int i = 0; i <= maxK; i++)
        {
            freeLists[i] = new FreeBlockList();
        }
        
        //Initializes only one large block under the max k value list
        freeLists[maxK].add(new FreeBlock(0, startSize));
    }
    
    
  
    public MemHandle insert(byte[] info) {
        //Finds the minimum block size required to hold this data
        int sizeRequired = nextPowerOfTwo(info.length);
        //Finding the index of the freeList field to look for a FreeBlock object
        int k = log2(sizeRequired);
        //The status of the allocation starts as false
        boolean allocated = false;
        //The current FreeBlockList to look at
        int i = k;
        
        
        //Runs until the block has been allocated, searching for free blocks, 
        //  then doubling the list if no space is found
        while (!allocated)
        {
            i = k;
            //Finding the smallest available block
            while (i <= maxK && freeLists[i].isEmpty()) {
                i++;
            }
            
            //If the iterations stop before the end of the array, we have found 
            //  a block available
            if (i <= maxK) {
                allocated = true;
            } 
            //If the iterations go until the last value, the block was not 
            //  found and the poolSize needs to double.
            else 
            {
                byte[] newPool = new byte[poolSize * 2];
                System.arraycopy(memoryPool, 0, newPool, 0, poolSize);
                memoryPool = newPool;

             // Step 1: create a new FreeBlockList array of size maxK+2
                FreeBlockList[] newFreeLists = new FreeBlockList[maxK + 2];

                // Step 2: copy old lists into the new array
                for (int j = 0; j <= maxK; j++) {
                    newFreeLists[j] = freeLists[j];
                }

                // Step 3: initialize the new slot for the new maxK
                newFreeLists[maxK + 1] = new FreeBlockList();

                // Step 4: replace old freeLists array with new one
                freeLists = newFreeLists;

                // Step 6: add the new block for the newly added memory
                FreeBlock newBlock = new FreeBlock(poolSize, poolSize);
                
                // Step 5: update maxK and poolSize
                poolSize *= 2;
                maxK++;
                
                release(new MemHandle(newBlock.start, newBlock.size, info.length));
            }
        }
        
        // Found a block in freeLists[i], allocate it here
        FreeBlock block = freeLists[i].pop();
        
        //Using the found FreeBlockList with a FreeBlock available, we split 
        //  until it is the correct size for our data
        while (i > k)
        {
            //If the block is larger than the size required, then we subtract 
            //  by one to find the index if we were to split it in half.
            i--;
            
            //Calculating the size of the new blocks
            int half = block.size / 2;
            
            //Creating the block's buddy within itself, dividing the memory by 2
            FreeBlock buddy = new FreeBlock(block.start + half, half);
            
            //Add the buddy block to the feeLists array at the size it was 
            //  split in half to
            freeLists[i].add(buddy);

            //Now, we have an updated size of the block we will use.
            block = new FreeBlock(block.start, half);
        }
        
        //Copying memory to new block portion
        for (int n = 0; n < info.length; n++)
        {
            memoryPool[block.start + n] = info[n];
        }
        
        //Returning a new memory handle including the memory start and size to 
        //  re-access when called
        return new MemHandle(block.start, block.size, info.length);
    }
    
    
    
    public void release(MemHandle h) {
        // Step 1: recreate the block from the handle
        FreeBlock block = new FreeBlock(h.getStart(), h.getBlockSize());
        int k = log2(block.size);

        // Step 2: try to find the buddy in the same free list
        //buddy address
        int buddyStart = block.start ^ block.size;
        FreeBlock buddy = freeLists[k].findBuddy(buddyStart);

        if (buddy == null) {
            // No buddy found, just add to the free list
            freeLists[k].add(block);
        } 
        else 
        {
            // Buddy found, remove both block and buddy from the free list
            freeLists[k].remove(buddy);

            // Merge the two blocks into one larger block
            int mergedStart = Math.min(block.start, buddy.start);
            FreeBlock merged = new FreeBlock(mergedStart, block.size * 2);

            // Step 3: recursively release the merged block
            release(new MemHandle(merged.start, merged.size, h.getRecordSize()));
        }
    }
    
    public byte[] getRecord(MemHandle h) {
        byte[] data = new byte[h.getRecordSize()];
        int memStart = h.getStart();
        int memSize = h.getRecordSize();
        
        for (int i = 0; i < memSize; i++)
        {
            data[i] = memoryPool[memStart + i];
        }
        return data;
    }
    
    
    
    
    /**Checks if value is a power of two by subtracting 1 to flip 
     * the bit and all bits below it, then AND'ing them, which 
     * would result in a 0 if value is a power of 2
     * @param x
     * @return True if power of 2, false if not
     */
    public boolean isPowerOfTwo(int x)
    {
        return (x & (x - 1)) == 0;
    }
    
    
    
    /**Starts with an integer with a value of 1, then shifts the bits to the 
     * left while p is less than x (the value being evaluated). This will 
     * keep p as a power of 2 and only stop when we find the next largest 
     * power of 2.
     * @param x 
     * @return The next highest power of 2
     */
    public int nextPowerOfTwo(int x) 
    {
        int p = 1;
        while (p < x)
        {
            p <<= 1;
        }
        return p;
    }
    
    
    
    /**This function calculates a log2 function by shifting the bits to the 
     * right of the parameter (dividing by 2) until it is equal to one, 
     * tracking the number of times it took to shift to the right, then 
     * returning that value representing k.
     * @param x 
     * @return log2(x)
     */
    public int log2(int x)
    {
        int r = 0;
        while (x > 1)
        {
            x = x >> 1;
            r++;
        }
        return r;
    }
}
