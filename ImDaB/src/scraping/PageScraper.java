package scraping;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.Node;

public abstract class PageScraper implements Runnable {

	private String location;
	private String selectQuery;
	private Node node;

	/**
	 * Constructs the PageScraper
	 * @param location The URL of the page (after the https://www.imdb.com prefix)
	 */
	public PageScraper(String location, String selectQuery) {
		int index = location.lastIndexOf("?ref_=");
		if (index > 0)
			location = location.substring(0, index);
		this.node = new Node(location, Process.getInstance().getGraph());
		this.location = location;
		this.selectQuery = selectQuery;
	}

	@Override
	public void run() {
		Document doc = null;
		try {
			doc = Jsoup.connect(location).get();
			Elements els = doc.select(selectQuery);
			System.out.println(location);
        	for (Element el : els) {
        		exec(el);
        	}
		} catch(IOException e) {
			e.printStackTrace();
		}
		Process.getInstance().enqueue(getResult());
	}

	/**
	 * @return The node connected to this page
	 */
	public Node getNode() {
		return node;
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
