package scraping;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Process {

	private ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 15, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), (r, e) -> {
		PageScraper scraper = (PageScraper) r;
		System.out.print(scraper.getNode().getName() + " has not been processed correctly");
	});

	private static Process instance;
	public static Process getInstance() {
		if (instance == null)
			instance = new Process();
		return instance;
	}
	private Process() {

	}

	public void enqueue(PageScraper scraper) {
		pool.execute(scraper);
	}

	public void enqueue(Iterable<PageScraper> scrapers) {
		for (PageScraper scraper : scrapers)
			pool.execute(scraper);
	}

	public boolean isDone() {
		return pool.getActiveCount() == 0;
	}

}
