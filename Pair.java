import java.util.ArrayList;

/**
 * Class needed in order to make the priority queue function.
 * essentially makes a tuple of a List<String> and an integer (Priority),
 * so each ladder can be associated with a priority and put into the correct
 * spot in the priority queue
 */
public class Pair {
		private ArrayList<String> pair;
		private int priority;
		
		/**
		 * constructor. accepts a ladder and a priority to be coupled together
		 * @param ladder - arraylist of a path of wikipedia pages
		 * @param priority - the priority of the ladder
		 */
		public Pair (ArrayList<String> ladder, int priority) {
			pair = ladder;
			this.priority = priority;	
		}
		
		/*
		 * allows for quick access of the priority value of the ladder
		 * @returns - priority - int
		 */
		public int getPriority() {
			return priority;
		}
		
		/**
		 * allows for access of the ladder contained within the pair
		 * @return ladder - ArrayList<String> of the path of wikipedia pages
		 */
		public ArrayList<String> getLadder() {
			return pair;
		}
		/**
		 * gets the element at index i of the ladder 
		 * @param index - the index of the element to be accessed
		 * @return String - the element at index i
		 */
		
		public String get(int i) {
			return pair.get(i);
		}
		
	}