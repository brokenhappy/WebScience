package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import graph.Context;
import graph.GameContext;
import graph.Graph;
import graph.Node;

public class GraphBuilder {
	
	private Map<String, Node> nodes;
	
	private Graph graph;
	
	private ResultSet currentSet = null;
	
	public GraphBuilder() {
		graph = Graph.instance();
		nodes = new HashMap<String, Node>();
	}
	
	
	public void genGraph() {
		MySQL.processQuery("SELECT * FROM review",
			       (row) -> {
			    	   //cerate a review object
//			             for(int i = 0; i < row.length; i++) {
//			            	 System.out.print(row[i] + " ");
//			             }
			       });
	}
}
