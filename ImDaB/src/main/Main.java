package main;

import scraping.TopGameListScraper;

public class Main {

	public static void main(String[] args) {
		scraping.Process process = scraping.Process.getInstance();
		process.enqueue(new TopGameListScraper("https://www.imdb.com/search/title?title_type=video_game&view=simple&sort=num_votes,desc"));
		//process.enqueue(new TopGameScraper(1, 10));

		try {
			Thread.sleep(2000);
			while (!process.isDone()) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
