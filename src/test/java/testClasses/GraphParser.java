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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Syed Zain Rizvi
 * @author Mona Hosseinkhani
 */

/**
 * A SAX parser used to parse GraphML files created through yEd.
 * The parser is only able to provide the collection of Nodes and Edges 
 * as presented in the GraphML file.
 */
public class GraphParser{

	// Private constructor so this class cannot be instantiated from outside
	private GraphParser(){
	}
	
	/**
	 * Parses the given input stream and returns the Graph object of that input stream.
	 * @param graphIS The input stream to parse
	 * @return The Graph object representing the graph as described by the input
	 * @throws Exception 
	 */
	public static Graph getGraph(InputStream graphIS) throws Exception{
		GraphParser gp = new GraphParser();
		SAXGraphParser sgp = gp.new SAXGraphParser(graphIS);
		
		Map<String, Node> nodesMap = sgp.getNodesMap();
		Map<String, Node> nodes = new HashMap<String,Node>();
		
		if (sgp.nodeLabelsUsed()){
			// If all the nodes had their name field non-null and non-empty,
			// then put the nodes in a map, where the name is the key instead of
			// id.
			for (Node node : nodesMap.values()){
				if (nodes.containsKey(node.getName())){
					// If a node name is repeated, then throw exception
					throw new Exception("Multiple nodes with name: " + node.getName());
				}
				
				nodes.put(node.getName(), node);
			}
		} else {
			// Else, the map of nodes has node id as key
			nodes = nodesMap;
		}
		
		return new Graph(nodes, sgp.getEdgesMap(), sgp.getRelationIdentifiersMap());
	}
	
	
	private class SAXGraphParser extends DefaultHandler{
	
		private InputStream graph;		// The GraphML file as input stream
		private Map<String, Node> nodesMap;	// Collection of nodes
		private Map<String, Edge> edgesMap;	// Collection of edges
		private Map<Object, Object> relationIdentifiersMap; // Collection of relationIdentifiers
							// Identified through the edge label in yEd
		private Boolean nodeLabelsUsed;	// Flag to indicate if node labels were used
		
		// Buffers
		private String tempVal;
		private Node tempNode;
		private Edge tempEdge;
		
		/**
		 * @param graph The graph to parse
		 */
		public SAXGraphParser(InputStream graph){
			this.graph = graph;	// Set the graph
			// Instantiate the nodesMap and edgesMap
			this.nodesMap = new HashMap<String, Node>();
			this.edgesMap = new HashMap<String, Edge>();
			this.relationIdentifiersMap = new HashMap<Object,Object>();
			
			// Set nodeLabelsUsed to true. If even one node does not
			// have a label, this will be set to false.
			this.nodeLabelsUsed = true;
			
			parse();
		}
			
		public Map<String, Node> getNodesMap(){
			return this.nodesMap;
		}
		
		public Map<String, Edge> getEdgesMap(){
			return this.edgesMap;
		}
		
		public Map<Object, Object> getRelationIdentifiersMap(){
			return this.relationIdentifiersMap;
		}
		
		public Boolean nodeLabelsUsed(){
			return this.nodeLabelsUsed;
		}
		
		public void parse(){
			SAXParserFactory spf = SAXParserFactory.newInstance();
			
			try {		// Try to parse the file
				
				//get a new instance of parser
				SAXParser sp = spf.newSAXParser();
				
				//parse the file and also register this class for call backs
				sp.parse(graph, this);
				
			}catch(SAXException se) {	// Catch and throw exception
				se.printStackTrace();
			}catch(ParserConfigurationException pce) {
				pce.printStackTrace();
			}catch (IOException ie) {
				ie.printStackTrace();
			}
		
		}
		
		//Event Handlers
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			//reset
			tempVal = "";
			if(qName.equalsIgnoreCase("Node")) {
				//create a new instance of node
				tempNode = new Node();
				// Set id
				tempNode.setId(attributes.getValue("id"));
			} else if (qName.equalsIgnoreCase("Edge")){
				//create a new instance of edge
				tempEdge = new Edge();
				// Set id
				tempEdge.setId(attributes.getValue("id"));
				// Set source and target
				Node source = nodesMap.get(attributes.getValue("source"));
				Node target = nodesMap.get(attributes.getValue("target"));
				tempEdge.setSource(source);
				tempEdge.setTarget(target);
			}
		}
		
		public void characters(char[] ch, int start, int length) throws SAXException {
			tempVal = new String(ch,start,length);
		}
		
		public void endElement(String uri, String localName, String qName) throws SAXException {
		
			if(qName.equalsIgnoreCase("Node")) {
				
				// Add it to the collection of nodes
				nodesMap.put(tempNode.getId(), tempNode);
				
				if (tempNode.getName() == null || tempNode.getName().isEmpty()){
					// If the node did not have a label or had an empty label
					// then set nodeLabelsUsed to false
					nodeLabelsUsed = false;
				}
				
			}else if (qName.equalsIgnoreCase("Edge")) {
				// Add it to the collection of edges
				edgesMap.put(tempEdge.getId(), tempEdge);
			}else if (qName.equalsIgnoreCase("y:NodeLabel")) {
				// Set node label
				tempNode.setName(tempVal);
			}else if (qName.equalsIgnoreCase("y:EdgeLabel")) {
				// Set relation identifier
				Object relationIdentifier = null;
				
				if (relationIdentifiersMap.containsKey(tempVal)){
					// already exists in map
					relationIdentifier = relationIdentifiersMap.get(tempVal);
				} else {
					// if doesn't already exist, then create and add
					relationIdentifier = new String(tempVal);
					relationIdentifiersMap.put(tempVal, relationIdentifier);
				}
				
				tempEdge.setRelationIdentifier(relationIdentifier);
			}
		}
	}
}
