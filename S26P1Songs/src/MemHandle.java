
// Handle class
public class MemHandle {
    private int start;  // start index in memoryPool
    private int size;   // size of allocated block

    public MemHandle(int start, int size) {
        this.start = start;
        this.size = size;
    }

    public int getStart() { return start; }
    public int getSize() { return size; }
}
