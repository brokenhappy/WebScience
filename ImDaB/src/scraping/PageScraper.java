package scraping;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.Node;

public abstract class PageScraper implements Runnable {

	private String location;
	private String selectQuery;
	private Node node = null;
	private boolean ignore = false; //if true, it will not be executed
	/**
	 * Constructs the PageScraper
	 * @param location The URL of the page (after the https://www.imdb.com prefix)
	 * @param selectQuery Selects the elements on the page, which are processed with the exec() method selects entire document if selectQuery == null
	 * @param addToGraph true if it should be added to the graph, false if it shouldn't
	 * @param origin The origin of the page, can be the a userID, a titleID or null
	 */
	public PageScraper(String location, String selectQuery, boolean addToGraph) {
		location = ScraperExpert.cleanURL(location);
		if (addToGraph) {
			if (ScraperExpert.getGraph().containsKey(location)) {
				node = ScraperExpert.getGraph().get(location);
				this.ignore = true;
				return;
			} else {
				node = new Node(location, ScraperExpert.getGraph());
			}
		}

		this.location = location;
		if (selectQuery != null)
			this.selectQuery = selectQuery.trim();
	}

	@Override
	public void run() {
		if (ignore)
			return;
		Document doc = null;
		try {
			System.out.println(location);
			doc = Jsoup.connect(location).get();
			if (selectQuery == null) {
				exec(doc);
			} else {
				Elements els = doc.select(selectQuery);
	        	for (Element el : els)
	        		exec(el);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		Iterable<PageScraper> results = getResult();
		if (results == null)
			return;

		if (node != null)
			for (PageScraper result : results)
				if (result.node != null)
					node.addNeighbour(result.node);
		Process.getInstance().enqueue(results);
	}

	/**
	 * @return The node connected to this page
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @return The URL of this page (without the http://www.imdb.com prefix)
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Is fired for every element found in the query
	 * @param element One of the elements found with the query
	 */
	public abstract void exec(Element element);

	/**
	 * Gets the result once it has been executed
	 */
	public abstract Iterable<PageScraper> getResult();

}
