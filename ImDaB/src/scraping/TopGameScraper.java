package scraping;

import java.util.LinkedList;
import java.util.regex.Matcher;

import org.jsoup.nodes.Element;

public class TopGameScraper extends PageScraper {

	private static final String prefix = "/search/title?title_type=video_game&sort=num_votes,desc&view=advanced&start=";
	private int nrOfTitlesToRead;
	private LinkedList<PageScraper> result = new LinkedList<PageScraper>();

	public TopGameScraper(int start) {
		super(prefix + Integer.toString(start), "#main > .article > .lister > .lister-list > .lister-item > .lister-item-content > .lister-item-header > a", false);
		nrOfTitlesToRead = 50;
	}

	public TopGameScraper(int start, int amount) {
		super(prefix + Integer.toString(start), "#main > .article > .lister > .lister-list > .lister-item > .lister-item-content > .lister-item-header > a", false);
		nrOfTitlesToRead = amount;
	}

	@Override
	public void exec(Element element) {
		if (nrOfTitlesToRead == 0)
			return;

		String ref = element.attr("href"); // formatted like "https://www.imdb.com/title/tt2103188/?ref_=adv_li_tt"
		if (ref == null || ref.isEmpty())
			return;

		Matcher matcher = ScraperExpert.getTitleIDFinder().matcher(ref);
		if (matcher.find()) {
			result.add(new GameReviewScraper(matcher.group(0).substring(1, 10)));
			nrOfTitlesToRead--;
		}
	}

	@Override
	public Iterable<PageScraper> getResult() {
		return result;
	}

}