/**
 * Copyright (c) 2013 ReBAC Java Library
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
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.xml.sax.SAXException;

import ca.ucalgary.ispia.rebac.parsers.RebacPolicyParser;
import ca.ucalgary.ispia.rebac.Policy;
import ca.ucalgary.ispia.rebac.util.Cache;
import ca.ucalgary.ispia.rebac.util.Constants;
import ca.ucalgary.ispia.rebac.util.SimpleCache;
import ca.ucalgary.ispia.rebac.util.Triple;
import testClasses.Edge;
import testClasses.Graph;
import testClasses.GraphParser;
import testClasses.Node;
import testClasses.TestFrame;

public class CircleFrameTest {

	Map <String, Node> nodes;
	Map <String, Edge> edges;
	Map <Object, Object> relationIdentifiers;
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
		
		Cache<Triple<Policy, Environment, Object>, Boolean> cache = new SimpleCache<Triple<Policy, Environment, Object>, Boolean>();		
		Constants.VariableConvention vc = Constants.VariableConvention.ResourceRequestor; 
		
		
		// Test with n0 as resource
		Assert.assertTrue(!ModelChecker.check(cache, frame, vc, n0, n0, policy));
		cache = new SimpleCache<Triple<Policy, Environment, Object>, Boolean>();
		Assert.assertTrue(ModelChecker.check(cache, frame, vc, n0, n1, policy));
		
		// Test with n1 as resource
		Assert.assertTrue(ModelChecker.check(cache, frame, vc, n1, n0, policy));
		cache = new SimpleCache<Triple<Policy, Environment, Object>, Boolean>();
		Assert.assertTrue(!ModelChecker.check(cache, frame, vc, n1, n1, policy));
		
		// Test policy
		String str = "(<1> (<1> (<1> req)))";
		Assert.assertTrue(str.equals(policy.toString()));
		
	}

	/**
	 * Test for exception.
	 */
	@Test(expected= SAXException.class)
	public void testException() throws SAXException{
		
		// Using getPolicies() method when the root of the tree is "policy"
		InputStream xmlIS = getClass().getResourceAsStream("/policyA.xml");
		RebacPolicyParser.getPolicies(xmlIS, new HashMap<Object, Object>(), true);
	}
}
