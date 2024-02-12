import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author jonathan silvestri
 * @project WikiRacer
 * @description - Program takes the names of two wikipedia pages and finds
 * a path from the starting page to the target page exploring only links embedded
 * in the articles. if no path is found, then null is returned.
 *
 */

/**
 * wikiracer class
 * main code that handles the searching algorithm and searches thru the wikipedia
 * links. WIkipedia article names should be passed in through commandline
 * arg1- source page
 * arg2- target page
 *
 */
public class WikiRacer {
	private static int pagesVisited = 0;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		System.out.println("Start Page: " + args[0]);
		System.out.println("Target Page: " + args[1]);
		System.out.println();
		System.out.println("PATH:");
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println("\nPATH FOUND:");
		System.out.println(ladder);
		System.out.println("Pages Scraped: " + pagesVisited);
		System.out.println();
		long endTime = System.currentTimeMillis();
		long elapsed = (endTime - startTime) / 1000;
		System.out.println("Time to complete: " + elapsed + " seconds");
	}

	/**
	 * findWikiLadder finds the ladder of links that connects the start
	 * page and the target page.
	 * @param start - start page
	 * @param end - target page
	 * @return ladder - a list that shows the path from the start page to the target page
	 */
	private static List<String> findWikiLadder(String start, String end) {
		WikiScraper scraper = new WikiScraper();
		// Finds all the links on the target page
		Set<String> targetPageLinks = scraper.findWikiLinks(end);
		// creates the first ladder, containing the start page
		ArrayList<String> initialLadder = new ArrayList<String>();
		initialLadder.add(start);
		// makes a makeshift tuple, which associates a priority with a ladder called a pair
		Pair firstLadder = new Pair(initialLadder, 0);
		MaxPQ pq = new MaxPQ();
		pq.enqueue(firstLadder);
		// Set of visited pages
		Set<String> visited = new HashSet<String>();
		
		while(!pq.isEmpty()) {
			// highest priority ladder is dequeued
			ArrayList<String> topPath = pq.dequeue();
			System.out.println(topPath);
			// get page at end of ladder
			String currentPage = topPath.get(topPath.size() - 1);
			// find all links associated with the current page
			Set<String> currentPageLinks = scraper.findWikiLinks(currentPage);
			// add current page to visited
			visited.add(currentPage);
			// if the current page contains the target page, then a complete ladder has been found
			if (currentPageLinks.contains(end)) {
				topPath.add(end);
				return topPath;
			}
			
			currentPageLinks.parallelStream().forEach(link -> {
				scraper.findWikiLinks(link);
				});

			// otherwise, look at all the pages on the current page, and create a new ladder going to each of them
			for (String neighbor: currentPageLinks) {
				if (!visited.contains(neighbor)) {
					ArrayList<String> copy = new ArrayList<String>(topPath);
					copy.add(neighbor);
					Pair copiedLadder = new Pair(copy, WikiRacer.findLadderPriority(copy, targetPageLinks, scraper));
					// new ladders are enqueued for later use
					pq.enqueue(copiedLadder);
					pagesVisited++;

				}		
			}
		}
		// if no path is found, null is returned
		return null;
	}
	
	/**
	 * method will find the total priority of a ladder. the priority of a ladder is represented as the number
	 * of pages that the last item in the latter and the target page have in common
	 * @param ladder -  ArrayList<String> of links that represent the current path
	 * @param targetPageLinks - the set of links on the target page
	 * @param scraper - the wikiscraper object
	 * @return priority - integer that represents the priority of the ladder
	 */
	private static int findLadderPriority(ArrayList<String> ladder, Set<String> targetPageLinks, WikiScraper scraper ) {
		int priority = 0;
		// gets a set of related pages for the last item in ladder
		Set<String> nextElemSet = scraper.findWikiLinks(ladder.get(ladder.size()-1));
		// counts amount of related pages the target page and current page have.
		for (String item: nextElemSet) {
			if (targetPageLinks.contains(item))
				priority += 1;
		}
		return priority;
	}
}


