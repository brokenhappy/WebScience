package scraping;

import org.jsoup.nodes.Element;

public class GameScraper extends PageScraper {

	public GameScraper(String location) {
		super(location, "");
	}

	@Override
	public void exec(Element element) {

	}

	@Override
	public Iterable<PageScraper> getResult() {
		return null;
	}

}
