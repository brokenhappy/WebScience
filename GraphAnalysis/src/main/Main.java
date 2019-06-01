package main;

import java.util.ArrayList;
import java.util.LinkedList;

import graph.Graph;
import graph.Node;
import model.GraphBuilder;
import model.MySQL;

public class Main {

	public static void main(String[] args) {
		GraphBuilder builder = new GraphBuilder();
		builder.genGraph();
		builder.genEdges();
		
		Graph graph = Graph.instance();
		
		LinkedList<Integer> topUsers = new LinkedList<Integer>();
		MySQL.processQuery("SELECT `user` FROM review GROUP BY `user` ORDER BY COUNT(*) DESC", (row) -> {
			topUsers.add(Integer.parseInt((String) row[0]));
		});

		int componentCount = graph.getComponents();
		System.out.println(componentCount);

		for (int i = topUsers.size() - 1; i >= 0; i--) {
			componentCount = graph.getComponents();
			
			Node userNode = graph.getNode("ur" + Integer.toString(topUsers.removeFirst()));
			ArrayList<Node> toBeRemovedGames = new ArrayList<Node>();

			// Remove all game neighbours ONLY if it has no other neighbours then the current user
			for (Node node : userNode.getAdjacencies().keySet())
				if (node.getUrl().startsWith("tt") && node.getAdjacencies().size() == 1)
					toBeRemovedGames.add(node);
			
			// Remove them only after iterating to prevent concurrency issues
			for (Node node : toBeRemovedGames)
				graph.removeNode(node);
			
			graph.removeNode(userNode);
			System.out.print(topUsers.size());
			System.out.print(": ");
			System.out.println(componentCount);
		}
	}

}
