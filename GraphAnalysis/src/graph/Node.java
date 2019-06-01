package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Node {
	private String url;
	private Context context;
	//String = the link of a node
	private final HashMap<Node, Review> adjacencies = new HashMap<Node, Review>();
	
	public Node(String url, Context context) {
		this.url = url;
		this.context = context;
	}

	public void removeAdjecency(Node node) {
		adjacencies.remove(node);
	}
	
	public void addNeighbour(Node node, Review review) {
		adjacencies.put(node, review);
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

	public HashMap<Node, Review> getAdjacencies() {
		return adjacencies;
	}

	/**
	 * Finds all connected Nodes
	 * @param nodes The node list to append all connected nodes to
	 */
	void _depthFirstSearch(HashSet<Node> nodes) {
		nodes.add(this);
		//For each neighbor, add all neighbors if the node was not added before
		for(Node neighbor : adjacencies.keySet())
			if (nodes.add(neighbor)) 
				neighbor._depthFirstSearch(nodes);
	}

	/**
	 * Gives all connected Nodes
	 */
	public HashSet<Node> depthFirstSearch() {
		HashSet<Node> result = new HashSet<Node>();
		_depthFirstSearch(result);
		return result;
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Node)
			return ((Node) other).url == url;
		return false;
	}

}
