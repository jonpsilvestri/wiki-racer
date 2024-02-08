import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author jonathan silvestri
 * @project WikiScraper
 * @description - WikiScraper class has various methods to get and parse various information
 * from wikipedia. Can retrieve links and html from wikipedia pages.
 *
 */

/**
 * 
 * WikiScraper class has various methods to get and parse various information
 * from wikipedia. Can retrieve links and html from wikipedia pages. Can also
 * process and find links from HTML using regex
 *
 */
public class WikiScraper {
	// hashset of related links for pages. Used for memoization
	public static HashMap<String,Set<String>> pageSets = new HashMap<String,Set<String>>();
			
	/**
	 * finds all other links contained on the inputted link.
	 * @param link - link name of a wikipedia page
	 * @return links - Set<String> of all links contained on the specified page
	 */
	public Set<String> findWikiLinks(String link) {
		// if findWikiLinks method has already been called for link, saved return value is returned
		if (pageSets.containsKey(link))
			return pageSets.get(link);
		// fetches html from the page
		String html = fetchHTML(link);
		// set of links contained on specified page
		Set<String> links = scrapeHTML(html);
		// return value is saved with the link as its key, in case findWikiLinks with the same page is called again in the future.
		pageSets.put(link, links);
		return links;
	}
	
	/**
	 * fetches the HTML from the specified page
	 * @param link - name of the page to have its html fetched
	 * @return string of the HTML code from specified page
	 */
	private String fetchHTML(String link) {
		// new string buffer
		StringBuffer buffer = null;
		try {
			// grabs url for the page
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			// reads and appends the characters in the HTML code to the buffer
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			return fetchHTML(link);
		}
		// returns the buffer as a string
		return buffer.toString();
	}
	
	/**
	 * returns the url for any given wikipedia page name
	 * @param link -  name of the wikipedia page
	 * @return url - link = Dog --> https://en.wikipedia.org/wiki/Dog
	 */
	private String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
	/**
	 * scrapes the HTML code for links to other wikipedia pages. saves the links in a set
	 * @param html - string of the HTML code for a wikipedia page
	 * @return links - HashSet<String> of all the links contained on the specified page
	 */
	private Set<String> scrapeHTML(String html) {
		// calls helper function to look for all links in the html
		Set<String> links = matchFinder(html);
		return links;
	}
	
	/**
	 * helper function to find links within the page HTML. This function
	 * takes a string of the HTML scraped from the Wikipedia page, and then
	 * uses a RegularExpression to find links. The regular expression
	 * finds all instances of <a href="/wiki/ form that dont include
	 * "#" or ":" characters. After matches are found, they are trimmed
	 * to exclude the <a href... parth and the quotation mark at the end
	 * is also trimmed, leaving the name of the page. A list of all the page names
	 * contained within the html is returned
	 * @param html - the string of html code that makes up a wikipedia page
	 * @return allMatches - a set of link names contained within the html
	 */
	private Set<String> matchFinder(String html){
		 Set<String> allMatches = new HashSet<String>();
		 // compiles a regex statement to find matches in the html
		 Matcher m = Pattern.compile("<a href=\"/wiki/[^:#\"]+\"").matcher(html); // match looks like <a href="/wiki/ and has no : or # characters
		 while (m.find()) {
			 String match = m.group().substring(15);         // first 15 characters are trimmed off
			 match = match.substring(0, match.length() - 1); // trims last character (") off string
			 allMatches.add(match);
		 }
		 return allMatches;	
	}
	
}
