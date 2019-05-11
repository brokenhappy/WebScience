package scraping;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public abstract class PageScraper implements Runnable {

	private String location;
	private String selectQuery;
	private boolean ignore = false; //if true, it will not be executed
	/**
	 * Constructs the PageScraper
	 * @param location The URL of the page (after the https://www.imdb.com prefix)
	 * @param selectQuery Selects the elements on the page, which are processed with the exec() method selects entire document if selectQuery == null
	 * @param addToGraph true if it should be added to the graph, false if it shouldn't
	 * @param origin The origin of the page, can be the a userID, a titleID or null
	 */
	public PageScraper(String location, String selectQuery) {
		location = ScraperExpert.cleanURL(location);

		this.location = location;
		if (selectQuery != null)
			this.selectQuery = selectQuery.trim();
	}

	@Override
	public void run() {
		if (ignore)
			return;
		try {
			Document doc;
			//if we search for element with id="main"
			if (selectQuery != null && selectQuery.trim().startsWith("#main"))
				//Download raw body content, extract element with id="main" and parse it
				doc = Jsoup.parse(IDExtractor.extractElement(Jsoup.connect(location).execute().body()));
			else
				//Download and parse the entire document
				doc = Jsoup.connect(location).get();

			System.out.println(location);
			if (selectQuery == null)
				exec(doc);
			else
	        	for (Element el : doc.select(selectQuery))
	        		exec(el);

		} catch(IOException e) {
			e.printStackTrace();
		}
		Iterable<PageScraper> results = getResult();
		if (results == null)
			return;

		Process.getInstance().enqueue(results);
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
