package main;

import scraping.TopGameScraper;

public class Main {

	public static void main(String[] args) {
		scraping.Process process = scraping.Process.getInstance();
		process.enqueue(new TopGameScraper("https://www.imdb.com/search/title?title_type=video_game&view=simple&sort=num_votes,desc"));

		try {
			Thread.sleep(2000);
			while (!process.isDone())
				Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
