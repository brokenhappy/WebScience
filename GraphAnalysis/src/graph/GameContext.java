package graph;

import java.util.ArrayList;

public class GameContext extends Context {
	private String title;
	private ArrayList<String> categories;
	private int rating;
	private int nrOfVotes;
	
	public GameContext(int id, String title, ArrayList<String> categories, int rating, int nrOfVotes) {
		super(id);
		this.title = title;
		this.categories = categories;
		this.rating = rating;
		this.nrOfVotes = nrOfVotes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getCategories() {
		return categories;
	}

	public void addCategory(String category) {
		this.categories.add(category);
	}
	
	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
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
