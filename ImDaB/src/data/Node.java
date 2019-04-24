package data;

import java.util.HashMap;

public class Node {

	private HashMap<String, Node> neighbours = new HashMap<String, Node>();
	private String name;
	private Graph graph;

	public Node(String name, Graph graph) {
		this.graph = graph;
		this.name = name;
		graph.put(name, this);
	}

	public void addNeighbour(String name) {
		neighbours.put(name, graph.get(name));
	}

	public void addNeighbour(Node node) {
		neighbours.put(node.name, node);
	}

	public String getName() {
		return name;
	}
}
