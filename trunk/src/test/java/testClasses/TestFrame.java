package testClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.ucalgary.ispia.rebac.Direction;
import ca.ucalgary.ispia.rebac.Frame;

/**
 * @author Syed Zain Rizvi
 */
/**
 * Basic implementation of the Frame interface.
 * Consists of sets of Node and Edge objects.
 */
public class TestFrame implements Frame {

	private Map<String, Node> nodes;	// List of nodes (verticies)
	private Map<String, Edge> edges;	// List of edges (relationships)
	
	/**
	 * Constructor. Sets the list of nodes and edges
	 * @param nodes
	 * @param edges
	 */
	public TestFrame(Map<String, Node> nodes, Map<String, Edge> edges){
		this.nodes = nodes;
		this.edges = edges;
	}
	
	/**
	 * Finds the neighbours of the given vertex, of the given relation identifier type,
	 * in the given direction
	 */
	@Override
	public Iterable findNeighbours(Object vertex,
			Object relationIdentifier, Direction direction) {
		Node node = (Node) vertex;		// Cast the vertex to Node object
		List<Node> neighbours = new ArrayList<Node>();	// Instantiate the list of neighbours
		
		// Loop the the collection of edges (relationships)
		for (Edge edge : edges.values()){
			if (edge.getRelationIdentifier().equals(relationIdentifier)){
				// The relation identifier matches
				if (direction == Direction.FORWARD){
					if (edge.getSource()==null)
						System.out.println(edge);
					if (edge.getSource().equals(node)){
						// Direction = FORWARD
						// Given vertex is the source
						neighbours.add(edge.getTarget());
					}
				}
				else if (direction == Direction.BACKWARD){
					if (edge.getTarget()==null)
						System.out.println(edge);
					if (edge.getTarget().equals(node)){
						// Direction = BACKWARD
						// Given vertex is the target
						neighbours.add(edge.getSource());
					}
				}
				else if (direction == Direction.EITHER){
					// Direction = EITHER
					// Given vertex is either the source or the target
					if (edge.getSource()==null)
						System.out.println(edge);
					if (edge.getSource().equals(node)){
						neighbours.add(edge.getTarget());
					}
					else{
						if (edge.getTarget()==null)
						System.out.println(edge);
						if (edge.getTarget().equals(node)){
						neighbours.add(edge.getSource());
					}
					}
				}
			}
		}
		
		return neighbours;
	}

}
