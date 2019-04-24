package scraping;

import java.util.regex.Pattern;

import data.Graph;

public final class ScraperExpert {

	private static final Pattern userIDFinder = Pattern.compile("\\/ur[0-9]{7}\\/");
	private static final Pattern titleIDFinder = Pattern.compile("\\/tt[0-9]{7}\\/");
	private static final Graph graph = new Graph();

	public static Pattern getUserIDFinder() {
		return userIDFinder;
	}
	public static Pattern getTitleIDFinder() {
		return titleIDFinder;
	}
	public static Graph getGraph() {
		return graph;
	}

	public static String cleanURL(String url) {
		if (!url.startsWith("https://www.imdb.com"))
			url = "https://www.imdb.com" + url;
		int index = url.lastIndexOf("?ref_=");
		if (index > 0)
			return url.substring(0, index);
		return url;
	}

}
