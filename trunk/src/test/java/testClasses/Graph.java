/**
 * Copyright (c) 2013 szrrizvi
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

import java.util.Map;

/**
 * @author Syed Zain Rizvi
 * @author Mona Hosseinkhani
 */

/**
 * Represents a graph that is created using the Node and Edge objects. 
 */

public class Graph {
	// The maps of nodes, edges, and relation identifiers.
	// The keys in the map are the names/ids for objects
	// The values in the map are the actual objects
	private Map<String, Node> nodes;
	private Map<String, Edge> edges;
	private Map<Object, Object> relationIdentifiers;
	
	
	/**
	 * Constructor to initiazlize fields
	 * @param nodes The map of nodes
	 * @param edges The map of edges
	 * @param relationIdentifiers The map of relationIdentifiers
	 */
	public Graph(Map<String, Node> nodes, Map<String, Edge> edges,
			Map<Object, Object> relationIdentifiers) {
		// Set fields
		this.nodes = nodes;
		this.edges = edges;
		this.relationIdentifiers = relationIdentifiers;
	}

	// Setters and getters

	/**
	 * @return The map of nodes
	 */
	public Map<String, Node> getNodes() {
		return nodes;
	}

	/**
	 * @return The map of edges
	 */
	public Map<String, Edge> getEdges() {
		return edges;
	}

	/**
	 * @return The map of relation identifiers
	 */
	public Map<Object, Object> getRelationIdentifiers() {
		return relationIdentifiers;
	}	
}
