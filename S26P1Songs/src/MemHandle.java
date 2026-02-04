
// Handle class
public class MemHandle {
    private int start;  // start index in memoryPool
    private int blockSize;   // size of allocated block
    private int recordSize; //actual size of the record

    public MemHandle(int start, int blockSize, int recordSize) {
        this.start = start;
        this.blockSize = blockSize;
        this.recordSize = recordSize;
    }

    public int getStart() { return start; }
    public int getBlockSize() { return blockSize; }
    public int getRecordSize() { return recordSize; }
}
