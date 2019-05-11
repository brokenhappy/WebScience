package scraping;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;

import org.jsoup.nodes.Element;

import sql.MySQL;

public class TopGameScraper extends PageScraper {

	private static final String prefix = "/search/title?title_type=video_game&sort=num_votes,desc&view=advanced&start=";
	private int nrOfTitlesToRead;
	private LinkedList<PageScraper> result = new LinkedList<PageScraper>();

	public TopGameScraper(int start) {
		super(prefix + Integer.toString(start), "#main > .article > .lister > .lister-list > .lister-item > .lister-item-content");
		nrOfTitlesToRead = 50;
	}

	public TopGameScraper(int start, int amount) {
		super(prefix + Integer.toString(start), "#main > .article > .lister > .lister-list > .lister-item > .lister-item-content");
		nrOfTitlesToRead = amount;
	}

	@Override
	public void exec(Element element) {
		if (nrOfTitlesToRead == 0)
			return;

		Element titleEl = element.selectFirst(".lister-item-header > a");
		Element genreEl = element.selectFirst("p.text-muted > span.genre");
		Element ratingEl = element.selectFirst(".ratings-bar > .ratings-imdb-rating");
		Element votesEl = element.selectFirst(".sort-num_votes-visible > span[name='nv']");

		if (titleEl == null)
			return;

		String title = titleEl.html();

		ArrayList<String> genres = new ArrayList<String>();
		if (genreEl != null)
			for (String genre : genreEl.html().trim().split(", "))
				genres.add(genre);

		Byte rating = Byte.MAX_VALUE;
		if (ratingEl != null) {
			String value = ratingEl.attr("data-value");
			if (value.contains("."))
				rating = Byte.parseByte(value.replace(".", ""));
			else
				rating = Byte.parseByte(value + "0");
		}

		int nrOfVotes = -1;
		if (votesEl != null)
			nrOfVotes = Integer.parseInt(votesEl.attr("data-value"));

		String ref = titleEl.attr("href"); // formatted like "https://www.imdb.com/title/tt2103188/?ref_=adv_li_tt"
		if (ref == null || ref.isEmpty())
			return;

		Matcher matcher = ScraperExpert.getTitleIDFinder().matcher(ref);
		if (matcher.find()) {
			String titleID = matcher.group(0).substring(1, 10);
			result.add(new GameReviewScraper(titleID));
			nrOfTitlesToRead--;
			String titleIDWithoutTT = titleID.substring(2);
			for (String genre : genres)
				MySQL.enqueNonResultQuery("INSERT INTO `imdab_db`.`game_category` (`GameID`, `Category`) VALUES ("
						+ titleIDWithoutTT + ", \""
						+ genre + "\")");
			//keep on trying until its not blocked
			while(!MySQL.enqueNonResultQuery("INSERT INTO `imdab_db`.`game`(`ID`,`Rating`,`NrOfVotes`,`Title`) VALUES ("
					+ titleIDWithoutTT + ", "
					+ Byte.toString(rating) + ", "
					+ Integer.toString(nrOfVotes) + ", \""
					+ title + "\");"));
		}
	}

	@Override
	public Iterable<PageScraper> getResult() {
		return result;
	}

}