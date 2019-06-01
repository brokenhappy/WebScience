package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Graph {
	
	/**
	 * Makes the Edges compatible for hashing
	 */
	private class Edge {
		protected final int gameID;
		protected final int userID;
		protected final Review review;
		
		protected Edge(Review review) {
			this.review = review;
			this.gameID = review.getGame();
			this.userID = review.getUser();
		}
		
		@Override
		public int hashCode() {
			return gameID * 2372599 + userID * 3612341;
		}
		
		@Override
		public boolean equals(Object other) {
			if (other instanceof Edge)
				return ((Edge) other).gameID == gameID && ((Edge) other).userID == userID;
			return false;
		}
	}
	
	private final Map<String, Node> nodes = new HashMap<String, Node>();
	private final HashSet<Edge> edges = new HashSet<Edge>();
	
	private static Graph instance;
	
	private Graph() {}
	
	public static Graph instance() {
		if(instance == null)
			instance = new Graph();
		return instance;
	}
	
	public void addNode(Node node) {
		nodes.put(node.getUrl(), node);
	}
	
	public Node getNode(String url) {
		return nodes.get(url);
	}
	
	private Node getGameNode(int id) {
		return nodes.get("tt" + Integer.toString(id));
	}

	private Node getUserNode(int id) {
		return nodes.get("ur" + Integer.toString(id));
	}

	public boolean addEdge(Review review) {
		if (!edges.add(new Edge(review))) return false;
		
		Node gameNode = getGameNode(review.getGame());
		Node userNode = getUserNode(review.getGame());
		gameNode.addNeighbour(userNode, review);
		userNode.addNeighbour(gameNode, review);
		return true;
	}
	
}
