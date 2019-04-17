package scraping;

import java.util.LinkedList;

import org.jsoup.nodes.Element;

public class TopGameScraper extends PageScraper {

	private LinkedList<PageScraper> result = new LinkedList<PageScraper>();

	public TopGameScraper(String location) {
		super(location, "#main > .article > .desc > span:first-child");
	}

	@Override
	public void exec(Element element) {
		//extracting total titles from "1-50 of 23,217 titles." format
		String end = element.text().split(" of ")[1]; //remove everything before the total
		end = end.substring(0, end.length() - 8); //remove " titles."
		int total = Integer.parseInt(end.replace(",", "")); //remove comma
		for (int i = 1; i < total; i += 50)
			result.add(new SubScraper(getNode().getName() + "&start=" + Integer.toString(i)));
	}

	@Override
	public Iterable<PageScraper> getResult() {
		return result;
	}

	private class SubScraper extends PageScraper {

		public SubScraper(String location) {
			super(location, "#main > .article > .lister > .lister-list > .lister-item > .lister-item-content > .lister-col-wrapper > .col-title > .lister-item-header > span[title] > a");
		}

		private LinkedList<PageScraper> result = new LinkedList<PageScraper>();

		@Override
		public void exec(Element element) {
			String ref = element.attr("href");
			if (ref != null)
				result.add(new GameScraper(ref));
		}

		@Override
		public Iterable<PageScraper> getResult() {
			return result;
		}

	}

}
