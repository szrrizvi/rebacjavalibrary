package testClasses;

import java.util.Map;

public class Graph {
	private Map<String, Node> nodes;
	private Map<String, Edge> edges;
	private Map<Object, Object> relationIdentifiers;
	
	
	public Graph(Map<String, Node> nodes, Map<String, Edge> edges,
			Map<Object, Object> relationIdentifiers) {
		
		this.nodes = nodes;
		this.edges = edges;
		this.relationIdentifiers = relationIdentifiers;
	}


	public Map<String, Node> getNodes() {
		return nodes;
	}


	public Map<String, Edge> getEdges() {
		return edges;
	}


	public Map<Object, Object> getRelationIdentifiers() {
		return relationIdentifiers;
	}
	
	
	
	
}
