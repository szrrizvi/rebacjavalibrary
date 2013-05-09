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

package ca.ucalgary.ispia.rebac;

import java.io.InputStream;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import testClasses.Edge;
import testClasses.Graph;
import testClasses.GraphParser;
import testClasses.Node;


public class GraphParserTest{

	private Map<String, Node> nodes;
	private Map<String, Edge> edges;
	private Map<String, Object> relationIdentifiers;
	
	
	/**
	 * Sets up the fields before each test
	 */
	@Before
	public void setUp() throws Exception{
		
		// Get the graphml file and parse it
		InputStream graphIS = getClass().getResourceAsStream("/circleGraph.graphml");		
		
		Graph graph = GraphParser.getGraph(graphIS);
		
		// Get the collection of nodes, edges, and relation identifiers
		nodes = graph.getNodes();
		edges = graph.getEdges();
		relationIdentifiers = graph.getRelationIdentifiers();
	}
	
	/**
	 * Tests the node id=n0
	 */
	@Test
	public void testNodeN0(){
		Node node = nodes.get("0");
		
		assert(node != null);
		assert(node.getId().equals("n0"));
		assert(node.getName().equals("0"));
	}

	/**
	 * Tests the node id=n1
	 */
	@Test
	public void testNodeN1(){
		Node node = nodes.get("1");
		
		assert(node != null);
		assert(node.getId().equals("n1"));
		assert(node.getName().equals("1"));
	}
	/**
	 * Test for extra nodes
	 */
	@Test
	public void testAllNodes(){
		// Remove all known nodes, and then the collection should be empty
		nodes.remove("0");
		nodes.remove("1");
		
		assert(nodes.isEmpty());
	}
	
	/**
	 * Tests the edge id=e0
	 */
	@Test
	public void testEdgeE0(){
		Edge edge = edges.get("e0");
		Node source = nodes.get("0");
		Node target = nodes.get("1");
		Object relationIdentifier = relationIdentifiers.get("1");
		
		assert(edge.getId().equals("e0"));
		assert(edge.getSource().equals(source));
		assert(edge.getTarget().equals(target));
		assert(edge.getRelationIdentifier().equals(relationIdentifier));
	}

	/**
	 * Tests the edge id=e1
	 */
	@Test
	public void testEdgeE1(){
		Edge edge = edges.get("e1");
		Node source = nodes.get("1");
		Node target = nodes.get("0");
		Object relationIdentifier = relationIdentifiers.get("1");
		
		assert(edge.getId().equals("e1"));
		assert(edge.getSource().equals(source));
		assert(edge.getTarget().equals(target));
		assert(edge.getRelationIdentifier().equals(relationIdentifier));
	}
	
	/**
	 * Test for extra edges
	 */
	@Test
	public void testAllEdges(){
		// Remove all known edges, then the collection should be empty
		edges.remove("e0");
		edges.remove("e1");

		assert(edges.isEmpty());
	}
	
	/**
	 * Test for nodes when none of the nodes have names
	 */
	@Test
	public void testGraphNoNodeNames() throws Exception{
		InputStream graphIS = getClass().getResourceAsStream("/circleGraphNoNames.graphml");				
		Graph graph = GraphParser.getGraph(graphIS);
		
		Map<String, Node> nodes = graph.getNodes();
		
		nodes.remove("n0");
		nodes.remove("n1");
		
		assert(nodes.isEmpty());
	}
	
	/**
	 * Test for nodes when not all nodes have names
	 */
	@Test
	public void testGraphOneNodeName() throws Exception{
		InputStream graphIS = getClass().getResourceAsStream("/circleGraphOneName.graphml");				
		Graph graph = GraphParser.getGraph(graphIS);
		
		Map<String, Node> nodes = graph.getNodes();
		
		nodes.remove("n0");
		nodes.remove("n1");
		
		assert(nodes.isEmpty());
	}
	
	/**
	 * Test for nodes when multiples nodes share the same name
	 */
	@Test(expected = Exception.class)
	public void testGraphSameNodeNames() throws Exception{
		InputStream graphIS = getClass().getResourceAsStream("/circleGraphSameName.graphml");				
		GraphParser.getGraph(graphIS);		
	}
}
