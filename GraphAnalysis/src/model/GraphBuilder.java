package model;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import graph.Graph;
import graph.Node;
import graph.Review;

public class GraphBuilder {

	private Map<String, Node> nodes;

	private Graph graph;

	private ResultSet currentSet = null;

	public GraphBuilder() {
		graph = Graph.instance();
		nodes = new HashMap<String, Node>();
	}

	public void genGraph() {
		// Create review objects
		MySQL.processQuery(
				"SELECT user, game, rating, date, helpfullnessPositive, helpfullnessNegative FROM review LIMIT 10",
				(row) -> {
					int user = (int) row[0];
					int game = (int) row[1];
					int rating = (int) row[2];
					LocalDate date = LocalDate.parse((String) row[3]);
					int helpfulnessPositive = (int) row[4];
					int helpfulnessNegative = (int) row[5];
					Review review = new Review(user, game, rating, date, helpfulnessPositive, helpfulnessNegative);

				});
	}

	public static void main(String[] args) {
		GraphBuilder builder = new GraphBuilder();
		builder.genGraph();
	}
}
