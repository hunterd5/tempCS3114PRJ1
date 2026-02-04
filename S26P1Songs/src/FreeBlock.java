/**
 * Represents a specific free region in the memory pool.
 * FreeBlock objects are compared by reference identity,
 * not by start/size equality.
 * 
 * @author hrdil
 * 
 * @version 1
 */
public class FreeBlock {
    //Fields
    /**Where the data starts*/
    public int start;
    /**The size of the data*/
    public int size;

    //Initializing the FreeBlock, setting its start point and size
    public FreeBlock(int start, int size) {
        this.start = start;
        this.size = size;
    }
}
