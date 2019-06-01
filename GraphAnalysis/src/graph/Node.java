package graph;

import java.util.HashMap;

public class Node {
	private String url;
	private Context context;
	//String = the link of a node
	private HashMap<String, Review> adjacencies;
	
	public Node(String url, Context context) {
		this.url = url;
		this.context = context;
	}

	public void addNeighbour(Node node, Review review) {
		adjacencies.put(node.url, review);
	}
	
	public boolean hasEdge(Node node) {
		return adjacencies.containsKey(node.url);
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public HashMap<String, Review> getAdjacencies() {
		return adjacencies;
	}

	public void setAdjacencies(HashMap<String, Review> adjacencies) {
		this.adjacencies = adjacencies;
	}
	
}
