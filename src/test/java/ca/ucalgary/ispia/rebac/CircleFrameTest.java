package ca.ucalgary.ispia.rebac;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import ca.ucalgary.ispia.rebac.parsers.RebacPolicyParser;
import ca.ucalgary.ispia.rebac.Policy;

import testClasses.Edge;
import testClasses.Graph;
import testClasses.GraphParser;
import testClasses.Node;
import testClasses.TestFrame;

public class CircleFrameTest {

	Map <String, Node> nodes;
	Map <String, Edge> edges;
	Map <String, Object> relationIdentifiers;
	Policy policy;
	Frame frame;
	
	@Before
	public void setUp() throws Exception{
		
		InputStream graphIS = getClass().getResourceAsStream("/circleGraph.graphml");
		
		Graph graph = GraphParser.getGraph(graphIS);
		
		// Get the collection of nodes, edges, and relation identifiers
		nodes = graph.getNodes();
		edges = graph.getEdges();
		relationIdentifiers = graph.getRelationIdentifiers();
		
		InputStream xmlIS = getClass().getResourceAsStream("/policyA.xml");
		
		try {
			policy = RebacPolicyParser.getPolicy(xmlIS, relationIdentifiers, false);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		frame = new TestFrame(nodes, edges);
	}
	
	@Test
	public void test() {
		
		Node n0 = nodes.get("0");
		Node n1 = nodes.get("1");
		
		
		// Test with n0 as resource
		assert(!ModelChecker.check(frame, n0, n0, policy));
		assert(ModelChecker.check(frame, n0, n1, policy));
		
		// Test with n1 as resource
		assert(ModelChecker.check(frame, n1, n0, policy));
		assert(!ModelChecker.check(frame, n1, n1, policy));
		
		// Test policy
		String str = "(<1> (<1> (<1> Requestor)))";
		assert(str.equals(policy.toString()));
		
	}

	/**
	 * Test for exception.
	 */
	@Test(expected= SAXException.class)
	public void testException() throws SAXException{
		
		// Using getPolicies() method when the root of the tree is "policy"
		InputStream xmlIS = getClass().getResourceAsStream("/policyA.xml");
		RebacPolicyParser.getPolicies(xmlIS, new HashMap<String, Object>(), true);
	}
}
