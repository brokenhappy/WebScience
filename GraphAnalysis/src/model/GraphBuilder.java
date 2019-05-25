package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	private Set<Review> reviews = new HashSet<Review>();
	
	private Graph graph;

//	private ResultSet currentSet = null;

	public GraphBuilder() {
		graph = Graph.instance();
	}

	public void genGraph() {
		//Put categories of games into map
		//Doesn't work as there is no data in the game_category table
		MySQL.processQuery("SELECT gameID, Category FROM game_category LIMIT 10", (row) -> {
			int id = Integer.parseInt((String) row[0]);
			String category = (String) row[1];
			categories.get(id).add(category);
		});
		
		
		// Create Node objects and add them to graph
		MySQL.processQuery("SELECT ID, name FROM user LIMIT 10", (row) -> {
			int id = Integer.parseInt((String) row[0]);
			String url = String.format("ur%07d", id);
			String name = (String) row[1];
			UserContext context = new UserContext(id, name);
			Node node = new Node(url, context);
			nodes.put(url, node);
		});
		
		MySQL.processQuery("SELECT ID, Rating, NrOfVotes, Title FROM game LIMIT 10", (row) -> {
			int id = Integer.parseInt((String) row[0]);
			String url = String.format("tt%07d", id);
			int rating = Integer.parseInt((String) row[1]);
			int nrOfVotes = Integer.parseInt((String) row[2]);
			String title = (String) row[3];
			GameContext context = new GameContext(id, title, categories.get(id), rating, nrOfVotes);
			Node node = new Node(url, context);
			nodes.put(url, node);
		});
		
		
		//IDK wtf im doing - Bindu

		// Create review objects
//		MySQL.processQuery(
//				"SELECT user, game, rating, date, helpfullnessPositive, helpfullnessNegative FROM review LIMIT 10",
//				(row) -> {
//					int user = (int) row[0];
//					int game = (int) row[1];
//					int rating = (int) row[2];
//					LocalDate date = LocalDate.parse((String) row[3]);
//					int helpfulnessPositive = (int) row[4];
//					int helpfulnessNegative = (int) row[5];
//					Review review = new Review(user, game, rating, date, helpfulnessPositive, helpfulnessNegative);
//
//				});
	}

	public static void main(String[] args) {
		GraphBuilder builder = new GraphBuilder();
		builder.genGraph();
		for(Node node : builder.nodes.values()) {
			GameContext con = (GameContext) node.getContext();
			System.out.println(node.getUrl() + " " + con.getNrOfVotes());
		}
	}
}
