package scraping;

import java.util.LinkedList;
import java.util.regex.Matcher;
import org.jsoup.nodes.Element;


public class GameReviewScraper extends PageScraper {

	private LinkedList<PageScraper> result = new LinkedList<PageScraper>();
	private String titleID = null;

	public GameReviewScraper(String titleID) {
		super("/title/" + titleID + "/reviews", "#main > .article > .lister", true);
		this.titleID = titleID;
	}

	@Override
	public void exec(Element element) {
		String paginationKey = processListElement(element);
		while(paginationKey != null && !paginationKey.isEmpty()) { //Keep on scraping the reviews until all reviews have been added to the result
			SubReviewScraper subScraper = new SubReviewScraper(paginationKey);
			subScraper.run();
			paginationKey = subScraper.getPaginationKey();
		}
	}

	@Override
	public Iterable<PageScraper> getResult() {
		return result;
	}

	//This processes the reviews, which is used by both the GameReviewScraper as well as its SubScraper
	private String processListElement(Element element) {
		for (Element linkElement : element.select(".lister-list > .lister-item > .review-container > .lister-item-content > .display-name-date > .display-name-link > a")) {
			String ref = linkElement.attr("href"); // formatted like: "https://www.imdb.com/user/ur2103188/?ref_=adv_li_tt"
			if (ref == null || ref.isEmpty())
				continue;

			Matcher matcher = ScraperExpert.getUserIDFinder().matcher(ref);
			if (matcher.find())
				result.add(new UserReviewScraper(matcher.group(0).substring(1, 10)));
		}
		Element loadData = element.selectFirst(".load-more-data");
		if (loadData == null)
			return null;
		return loadData.attr("data-key");
	}

	//The SubScraper is not executed on different threads, these are executed on the same thread until all reviews are added
	private class SubReviewScraper extends PageScraper {

		private String paginationKey = null;

		public SubReviewScraper(String paginationKey) {
			super("/title/" + titleID + "/reviews/_ajax?paginationKey=" + paginationKey, null, false);
		}

		@Override
		public void exec(Element element) {
			paginationKey = processListElement(element);
		}

		protected String getPaginationKey() {
			return paginationKey;
		}

		@Override
		public Iterable<PageScraper> getResult() {
			return null;
		}

	}

}
