package scraping;

import java.util.LinkedList;

import org.jsoup.nodes.Element;

public class TopGameListScraper extends PageScraper {

	private LinkedList<PageScraper> result = new LinkedList<PageScraper>();

	public TopGameListScraper(String location) {
		super(location, "#main > .article > .desc > span:first-child", false);
	}

	@Override
	public void exec(Element element) {
		//extracting total titles from "1-50 of 23,217 titles." format
		String end = element.text().split(" of ")[1]; //remove everything before the total
		end = end.substring(0, end.length() - 8); //remove " titles."
		int total = Integer.parseInt(end.replace(",", "")); //remove comma
		for (int i = 1; i < total; i += 50)
			result.add(new TopGameScraper(i));
	}

	@Override
	public Iterable<PageScraper> getResult() {
		return result;
	}

}
