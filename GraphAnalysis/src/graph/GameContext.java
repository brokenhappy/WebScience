package graph;

public class GameContext extends Context {
	private String title;
	private String category;
	private int rating;
	private int nrOfVotes;
	
	public GameContext(int id, String title, String category, int rating, int nrOfVotes) {
		super(id);
		this.title = title;
		this.category = category;
		this.rating = rating;
		this.nrOfVotes = nrOfVotes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getNrOfVotes() {
		return nrOfVotes;
	}

	public void setNrOfVotes(int nrOfVotes) {
		this.nrOfVotes = nrOfVotes;
	}
	
}
