/**
 * Handle used for accessing data from the memory manager
 *
 * @author Katelyn Cao, Hunter Dillon
 * @version feb 6, 2026
 */
public class MemHandle {
	/** Start index in the memory pool */
    private int start;
    /** Size of the allocated block */
    private int blockSize;
    /** Actual size of the record */
    private int recordSize;

    /**
     * Constructor function for a memory handle
     * 
     * @param start - The block start position within the memory pool
     * @param blockSize - The size of the allocated block
     * @param recordSize - The size of the actual record
     */
    public MemHandle(int start, int blockSize, int recordSize) {
        this.start = start;
        this.blockSize = blockSize;
        this.recordSize = recordSize;
    }

    /**
     * Getter method for the block start
     * 
     * @return The block's starting position
     */
    public int getStart() { return start; }
    
    /**
     * Getter method for the block's allocated size
     * 
     * @return The block's allocated size
     */
    public int getBlockSize() { return blockSize; }
    
    /**
     * Getter method for the actual size of a record
     * 
     * @return The actual size of the record
     */
    public int getRecordSize() { return recordSize; }
}
