package graph;

import java.time.LocalDate;

public class Review {
	private int user;
	private int game;
	private int rating;
	private LocalDate date;
	private int helpfulnessPositive;
	private int helpfulnessNegative;
	
	public Review(int user, int game, int rating, LocalDate date, int helpfulnessPositive, int helpfulnessNegative) {
		super();
		this.user = user;
		this.game = game;
		this.rating = rating;
		this.date = date;
		this.helpfulnessPositive = helpfulnessPositive;
		this.helpfulnessNegative = helpfulnessNegative;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getGame() {
		return game;
	}

	public void setGame(int game) {
		this.game = game;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getHelpfulnessPositive() {
		return helpfulnessPositive;
	}

	public void setHelpfulnessPositive(int helpfulnessPositive) {
		this.helpfulnessPositive = helpfulnessPositive;
	}

	public int getHelpfulnessNegative() {
		return helpfulnessNegative;
	}

	public void setHelpfulnessNegative(int helpfulnessNegative) {
		this.helpfulnessNegative = helpfulnessNegative;
	}
	
	
	
}
