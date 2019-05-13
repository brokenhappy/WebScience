package graph;

import java.util.HashMap;
import java.util.Map;

public class Graph {
	
	private int numNodes = 0;
	
	private Map<String, Node> nodes = new HashMap<String, Node>();
	
	private static Graph instance;
	
	private Graph() {}
	
	public static Graph instance() {
		if(instance == null) {
			instance = new Graph();
		}
		return instance;
	}
	
	public void addNode(Node node) {
		nodes.put(node.getUrl(), node);
		numNodes++;
	}
	
	public Node getNode(String url) {
		return nodes.get(url);
	}
	
	public void setNodes(Map<String, Node> nodes) {
		this.nodes = nodes;
	}
	
}
