package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.GameContext;
import graph.Graph;
import graph.Node;
import graph.Review;
import graph.UserContext;

public class GraphBuilder {

	private Map<String, Node> nodes = new HashMap<String, Node>();
	private Map<Integer, ArrayList<String>> categories = new HashMap<Integer, ArrayList<String>>();

	private Graph graph;

//	private ResultSet currentSet = null;

	public GraphBuilder() {
		graph = Graph.instance();
	}

	public void genGraph() {
		// Put categories of games into map
		// Doesn't work as there is no data in the game_category table

		MySQL.processQuery("SELECT gameID, Category FROM game_category", (row) -> {
			int id = Integer.parseInt((String) row[0]);
			String category = (String) row[1];
			categories.get(id).add(category);
		});

		// Create user node objects
		MySQL.processQuery("SELECT ID, name FROM user LIMIT 1000", (row) -> {
			int id = Integer.parseInt((String) row[0]);
			String url = (String) row[0];
			String name = (String) row[1];
			UserContext context = new UserContext(id, name);
			Node node = new Node(url, context);
			nodes.put(url, node);
		});

		// Create game node objects
		MySQL.processQuery("SELECT ID, Rating, NrOfVotes, Title FROM game", (row) -> {
			int id = Integer.parseInt((String) row[0]);
//			String url = String.format("tt%07d", id);
			String url = (String) row[0];
			int rating = Integer.parseInt((String) row[1]);
			int nrOfVotes = Integer.parseInt((String) row[2]);
			String title = (String) row[3];
			GameContext context = new GameContext(id, title, categories.get(id), rating, nrOfVotes);
			Node node = new Node(url, context);
			nodes.put(url, node);
		});

	}

	public void genEdges() {
		// IDK wtf im doing - Bindu

		List<Review> reviews = new ArrayList<Review>();
		
		// Create review objects
		MySQL.processQuery("SELECT user, game, rating, date, helpfullnessPositive, helpfullnessNegative FROM review", (row) -> {
			int user = Integer.parseInt((String) row[0]);
			int game = Integer.parseInt((String) row[1]);
			int rating = Integer.parseInt((String) row[2]);
			LocalDate date = LocalDate.parse((String) row[3]);
			int helpfulnessPositive = Integer.parseInt((String) row[4]);
			int helpfulnessNegative = Integer.parseInt((String) row[5]);
			Review review = new Review(user, game, rating, date, helpfulnessPositive, helpfulnessNegative);
			reviews.add(review);
//			System.out.println(review.getUser());

		});
//		for(Review review : reviews) {
//			
//		}
	}

	public static void main(String[] args) {
		GraphBuilder builder = new GraphBuilder();
		builder.genGraph();
//		System.out.println(builder.nodes.size());
//		for(Node node : builder.nodes.values()) {
//			GameContext con = (GameContext) node.getContext();
//			System.out.println(node.getUrl() + " " + con.getNrOfVotes());
//		}
	}
}
