import java.util.ArrayList;

/**
 * 
 * @author jonathan silvestri
 * @project PA10BONUS - MaxPQ
 * @class CSc 210 Fall 2021
 * @description - Priority Queue for the WikiRace ladders. Custom Priority queue
 * based upon a binary Max-Heap. supports just the essential priority queue methods (enqueue/dequeue)
 *
 */

/**
 * Priority Queue for the WikiRace ladders. Custom Priority queue
 * based upon a binary Max-Heap. supports just the essential priority queue methods
 */
public class MaxPQ {
	private MaxHeap pq;
	
	public MaxPQ() {
		pq = new MaxHeap();
	}
	
	/**
	 * enqueues a Pair object. a pair is simply an arraylist with a priority field
	 * so priorities musn't be recalculated more than once per ladder
	 * @param ladder - a path for wikipedia ladders.
	 */
	public void enqueue(Pair ladder)
	{
		pq.add(ladder);
	}
	
	/**
	 * enqueues a pair into the queue. 
	 * @param ladder - ArrayList<String> and arraylist of strings that shows a path
	 * @param priority - int, the priority value of the ladder
	 */
	public void enqueue(ArrayList<String> ladder, int priority) {
		// constructs a Pair object and adds it to the heap
		Pair pair = new Pair(ladder, priority);
		pq.add(pair);
	}
	
	/**
	 * isEmpty() tells if the priority queue is empty
	 * @return boolean - true if the queue is empty. False otherwise
	 */
	public boolean isEmpty() {
		if (pq.size == 0)
			return true;
		return false;
	}
	
	/**
	 * returns the amount of elements that are in the queue
	 * @return size - number of elements in the queue
	 */
	public int size() {
		return pq.size;
	}
	
	/**
	 * Removes the highest priority patient in the queue and returns
	 * the name as a string. throws and exception if the queue is empty
	 * @return name - a string of the patient's name
	 */
	public ArrayList<String> dequeue() {
		// saves the return value
		Pair pair = pq.remove();
		// gets the ladder from the pair, and returns the list
		ArrayList<String >ladder = pair.getLadder();
		return ladder;
	}
	
	/**
	 * private binary MaxHeap class that supports the priority queue. This class is made by a 
	 * array of Pair objects, and starts with a default capacity of 5. when the object fills up,
	 * the heap size is doubled
	 * @author jonat
	 *
	 */
	private class MaxHeap{
		private Pair[] heap;
		private static final int DEFAULT_CAPACITY = 5;
		private int size;
		
		private MaxHeap() {
			heap = new Pair[DEFAULT_CAPACITY];
			size = 0;
		}
		
		 /**
	     * adds a new node. nodes are added at the bottom of the heap, and then bubbled
	     * up to thier correct spot. If the heap is full, the heap will be doubled in capacity
	     * before the new node is added to the heap
	     * @param e - new element to be added to heap
	     */
	    private void add(Pair ladder) {
	    	// checks if the heap is full, and resizes if it is
	        if (size == heap.length-1)
	            resize(this.getCapacity() * 2);
	        
	        size++;
	        heap[size] = ladder;
	        bubbleUp(size);
	    }
	    
	    /**
	     * removes and returns the top element of the heap. the new root node is 
	     * bubbled down if it's child has a higher priority than it after removal
	     * @return e - the element at the top of the heap
	     */
	    private Pair remove(){
	        if (isEmpty())
	        	return null;

	        Pair temp = heap[1]; // saves top node
	        heap[1] = heap[size]; // moves element at the bottom of the heap to the top
	        size--;
	        bubbleDown(1); // bubbles down the new root node to its final spot
	        return temp;
	    }
	    
	    /**
	     * moves newly added elements at the bottom of the heap to thier correct position
	     * if a newly added element has a higher priority than it's parent,
	     * they switch spots and the method recurses
	     * @param i - index of the object in the heap
	     */
	    private void bubbleUp(int i) {
	        if (i != 1) 
	             if (heap[i].getPriority() > heap[parent(i)].getPriority()){
	                 //Swap W/ Parent
	                Pair temp = heap[parent(i)];
	                heap[parent(i)] = heap[i];
	                heap[i] = temp;
	                bubbleUp(parent(i));
	            }
	    }
	    
	    /**
	     * moves lower priority nodes down the heap. Compares the parent to each of its children
	     * and swaps the indexes of the tow.
	     * @param i - index of the parent node
	     */
	    private void bubbleDown(int i) {
	        if (left(i) <= size) {
	            int hpc = left(i);
	            if (right(i) <= size && heap[right(i)].getPriority() > heap[left(i)].getPriority())
	                hpc = right(i);
	            if (heap[hpc].getPriority() > heap[i].getPriority()){    
	                //hpc = HigherPriorityChild
	                Pair temp = heap[hpc];
	                heap[hpc] = heap[i];
	                heap[i] = temp;
	                bubbleDown(left(i));
	                bubbleDown(right(i));
	            }
	        }
	    }
	    /**
	     * gets the capacity of the heap. capacity is the total slots in the
	     * heap, not the amount of elements in it
	     * @return capacity - the amount of slots in the array
	     */
	    private int getCapacity(){
	    	return heap.length;
	    }
		
		/**
	     * resizes the array to twice its size when it fills up. this method is called
	     * every time the amount of elements in the heap is one less than its capacity
	     * since the first index of the heap will always be null
	     * @param capacity - new capacity of the array
	     */
	    private void resize(int capacity)
	    {
	    	Pair[] temp =  new Pair[capacity]; // creates new array with double the capacity 
	        for (int i=0; i < heap.length; i++) // this loop copies the elements from the heap to the temp array
	            temp[i] = heap[i];
	        
	        heap = temp; // heap is set equal to the newly created array
	    }
		
		/**
	     * returns the index of the parent of the object at index
	     * i. an element at any index i will have its parent at index i/2
	     * @param i - index of a child
	     * @return i/2 - the index of that child's parent
	     */
	    private int parent(int i){
	    	return i/2;
	    }
	    /**
	     * returns the index of the left child of a parent.
	     * @param i - index of the parent
	     * @return 2*i - index of that parent's left child
	     */
	    private int left(int i){
	    	return 2*i;
	    }
	    /**
	     * returns the index of the right child of a parent.
	     * @param i - index of the parent
	     * @return 2*i + 1 - index of the right child of the parent
	     */
	    private int right(int i){
	    	return 2*i + 1;
	    }
	    
	    /**
	     * returns a boolean of if the heap is empty or not
	     * @return true if the size is 0, false otherwise
	     */
	    private boolean isEmpty() {
	        return size == 0;
	    }
	}
}
