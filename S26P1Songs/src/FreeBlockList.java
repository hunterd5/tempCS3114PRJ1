//Single linked list to hold all the FreeBlocks within the memory manager
public class FreeBlockList {
    
    //Creating a private node class only relevant to the list.
    private static class Node {
        //Fields
        //Houses the FreeBlock as the data
        FreeBlock block;
        //Remembers the next node as a single linked list
        Node next;

        /**Initializes a node by setting the FreeBlock data within and 
         * setting the next node as null
         * 
         * @param block the FreeBlock being placed into the node
         */
        Node(FreeBlock block) {
            this.block = block;
            this.next = null;
        }
    }
    
    //Fields of list
    private Node head;
    
    //Constructor function
    public FreeBlockList() {
        head = null;
    }
    
    public String getStarts()
    {
    	StringBuilder ans = new StringBuilder();
    	Node curr = head;
    	while (curr != null)
    	{
    		ans.append(curr.block.start).append(" ");
    		curr = curr.next;
    	}
    	return ans.toString();
    }
    
    //Linked list operations
    //Adding a block to the list
    public void add(FreeBlock block)
    {
        Node n = new Node(block);
        n.next = head;
        head = n;
    }
    
    //Removing a block from the list
    public void remove(FreeBlock block) {
        //Checks if the list is empty
        if (head == null) return;

        //Checks if block is in  the first node, removes the node and 
        //  sets the head to the next node
        if (head.block == block) {
            head = head.next;
            return;
        }

        //Initialized node variables for search
        Node prev = head;
        Node curr = head.next;

        //while there is data in the nodes being checked
        while (curr != null) {
            /**if the current node has the data we are searching for, set 
            *   the next field of the previous node to the next field of 
            *   the current node
            */
            if (curr.block == block) {
                prev.next = curr.next;
                return;
            }
            //updating the nodes being searched each iteration
            prev = curr;
            curr = curr.next;
        }
        
    }
    
    /** retrieving the buddy FreeBlock by iterating through the 
     * start locations within a list
     * 
     * @param buddyStart the start index that we are looking for
     * 
     * @return the first found free block
     */
    public FreeBlock findBuddy( int buddyStart) {
        Node curr = head;
        while (curr != null) {
            if (curr.block.start == buddyStart) 
            {
                return curr.block;
            }
            curr = curr.next;
        }
        return null;
    }
    
    /** retrieving the data within the first node since we 
     * just need one list of each power of 2
     * 
     * @return the first found free block
     */
    public FreeBlock getFirst() {
        //If the head is null, return null, else, return the 
        //  data from the head node
        return (head == null) ? null : head.block;
    }
    
    //Checks if the list is empty by checking the head
    public boolean isEmpty() {
        return head == null;
    }
      
    //removes the first node within the list
    public FreeBlock pop()
    {
        //checks if list is empty
        if (head == null) return null;
        //Captures the head block
        FreeBlock b = head.block;
        //sets the list head to next node
        head = head.next;
        //returns old head block
        return b;
    } 
}
    
    
