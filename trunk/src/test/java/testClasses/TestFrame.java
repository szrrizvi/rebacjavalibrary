/**
 * Copyright (c) 2013 Syed Zain Rizvi
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
					if (edge.getSource().equals(node)){
						// Direction = FORWARD
						// Given vertex is the source
						neighbours.add(edge.getTarget());
					}
				}
				else if (direction == Direction.BACKWARD){
					if (edge.getTarget().equals(node)){
						// Direction = BACKWARD
						// Given vertex is the target
						neighbours.add(edge.getSource());
					}
				}
				else if (direction == Direction.EITHER){
					// Direction = EITHER
					// Given vertex is either the source or the target
					if (edge.getSource().equals(node)){
						neighbours.add(edge.getTarget());
					}
					else if (edge.getTarget().equals(node)){
						neighbours.add(edge.getSource());
					}
				}
			}
		}
		
		return neighbours;
	}

}
