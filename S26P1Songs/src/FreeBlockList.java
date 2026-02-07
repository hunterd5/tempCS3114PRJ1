/**
 * A single linked list holding free blocks within the memory manager
 *
 * @author Katelyn Cao, Hunter Dillon
 * @version feb 6, 2026
 */
public class FreeBlockList {

	/**
	 * Private Node class representing the nodes within the linked list only
	 * relevant to linked list class
	 */
	private static class Node {
		/** The FreeBlock stored within a single node */
		FreeBlock block;
		/** The next node within the linked list */
		Node next;

		/**
		 * Initializes a node by setting the FreeBlock data within and setting the next
		 * node as null
		 * 
		 * @param block the FreeBlock being placed into the node
		 */
		Node(FreeBlock block) {
			this.block = block;
			this.next = null;
		}
	}

	/**
	 * The first node found at the front of the linked list where all operations
	 * begin
	 */
	private Node head;

	/**
	 * Constructor function
	 */
	public FreeBlockList() {
		// Initializing the head to null for a new, empty list
		head = null;
	}

	/**
	 * Method to print out the starting position of all free blocks within the
	 * linked list
	 * 
	 * @return A string containing the start positions of all free blocks in a list
	 */
	public String getStarts() {
		// Creating a new string builder to store the return value
		StringBuilder ans = new StringBuilder();
		// Creating a current node holder to iterate through the list
		Node curr = head;
		// While the current node we are checking is not null...
		while (curr != null) {
			// Add the block's starting location to the string
			ans.append(curr.block.start).append(" ");
			// Iterate to the next node within the list
			curr = curr.next;
		}
		// Returning the final list of starting locations within the list
		return ans.toString();
	}

	/**
	 * Adding a block into the linked list
	 * 
	 * @param block - The freeBlock to be added into the list
	 */
	public void add(FreeBlock block) {
		// Creating a new node holding the block as it's data
		Node n = new Node(block);
		// The next field of this new block is initialized to
		// the old head of the list
		n.next = head;
		// The new node is then deemed as the head node
		head = n;
	}

	/**
	 * Removing a specific block from the linked list
	 * 
	 * @param block - The block to be removed
	 */
	public void remove(FreeBlock block) {
		// If the list is empty, exit the method
		if (head == null)
			return;

		// If block we are searching for is within the first node,
		if (head.block == block) {
			// Removing the head from the list by setting the second as the new head
			head = head.next;
			return;
		}

		// Setting up a previous and current node variables as the first and second
		// nodes to iterate through the list
		Node prev = head;
		Node curr = head.next;

		// While there is data in the nodes being checked...
		while (curr != null) {
			// Checks if the data within the current node matches search block
			if (curr.block == block) {
				// If so, removes the node from the list by setting the previous node's
				// next node field to the current next node
				prev.next = curr.next;
				return;
			}

			// Updating the nodes being searched each iteration
			prev = curr;
			curr = curr.next;
		}
	}

	/**
	 * Retrieving the buddy FreeBlock by iterating through the start locations
	 * within a list of the same size
	 * 
	 * @param buddyStart - The start index that we are looking for
	 * @return The FreeBlock with the start location we are looking for
	 */
	public FreeBlock findBuddy(int buddyStart) {
		// Setting the current node at the start of the list for iterations
		Node curr = head;
		// While there is data in the current node...
		while (curr != null) {
			// Check if node with data contains a block that starts at the desired
			// start point
			if (curr.block.start == buddyStart) {
				// Returns the block from the list that represents the buddy to a
				// block to be released
				return curr.block;
			}
			// Iterating through the nodes
			curr = curr.next;
		}
		// If the block with the desired start point is not found within the list,
		// return null
		return null;
	}

	/**
	 * Retrieving the FreeBlock within the first node
	 * 
	 * @return The first found free block
	 */
	public FreeBlock getFirst() {
		// If the head is null, return null, else, return the
		// data from the head node
		return (head == null) ? null : head.block;
	}

	/**
	 * Checks if list is empty via if the head is null
	 * 
	 * @return True if head is null, False if not
	 */
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * Removes the first node from a list
	 * 
	 * @return - The block within the first node
	 */
	public FreeBlock pop() {
		// checks if list is empty
		if (head == null)
			return null;
		// Captures the head block
		FreeBlock b = head.block;
		// sets the list head to next node
		head = head.next;
		// returns old head block
		return b;
	}
}
