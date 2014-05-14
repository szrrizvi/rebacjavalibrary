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

package ca.ucalgary.ispia.rebac.parsers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import ca.ucalgary.ispia.rebac.Direction;
import ca.ucalgary.ispia.rebac.impl.AtImpl;
import ca.ucalgary.ispia.rebac.impl.BindImpl;
import ca.ucalgary.ispia.rebac.impl.BoxImpl;
import ca.ucalgary.ispia.rebac.impl.ConjunctionImpl;
import ca.ucalgary.ispia.rebac.impl.DiamondImpl;
import ca.ucalgary.ispia.rebac.impl.DisjunctionImpl;
import ca.ucalgary.ispia.rebac.impl.FalseImpl;
import ca.ucalgary.ispia.rebac.impl.NegationImpl;
import ca.ucalgary.ispia.rebac.impl.RequestorImpl;
import ca.ucalgary.ispia.rebac.impl.TrueImpl;
import ca.ucalgary.ispia.rebac.impl.VariableImpl;
import ca.ucalgary.ispia.rebac.Policy;


/**
 * @author Syed Zain Rizvi
 */

/**
 * ReBAC policy parser. Validates and parses xml files against the rebacPolicy.xsd schema
 */
public class RebacPolicyParser {

	private InputStream xmlIS;				// XML input stream
	private List<Policy> policiesObj;		// List contain policies
	private Policy policyObj;				// Single policy
	private Map<Object, Object> relationIdentifiers;	// Map containing relation identifiers
	private Boolean populateRelIds;				// Flag to check if relation identifiers should be populated
	
	/**
	 * Constructor to initializes the xmlFile, schemaFile, and the collection of relation identifiers.
	 * @param xmlIS The xml input stream
	 * @param relationIdentifiers The collection of recognizable relation identifiers
	 */
	private RebacPolicyParser(InputStream xmlIS, Map<Object, Object> relationIdentifiers, Boolean populateRelIds){
		
		// Initialize the xmlIs, relationIdentifiers, and populateRelIds
		this.xmlIS = xmlIS;
		this.relationIdentifiers = relationIdentifiers;
		this.populateRelIds = populateRelIds;
		
		// Initialize policyObj and policiesObj
		policiesObj = null;
		policyObj = null;
	}
	
	/**
	 * Parses the given policy input stream. The relationIdentifiers map is used while creating the policy objects. 
	 * The relation identifiers are matched through the key and the value in the relId tag in the input stream.<br/>
	 * EX: &lt;relId direction="forward"&gt;friend&lt;/relId&gt;<br/>
	 * The above example would match a relation identifier object in the map with with key "friend".<br/>
	 * If the key is not matched in the map and populateRelIds is true, then a new relation identifier object
	 * will be created and added to the map. NOTE: This object will be of type String.</br>
	 * If the key is not matched in the map and populateRelIds is false, then an exception will be thrown 
	 * @param policyIS The input stream to parse
	 * @param relationIdentifiers The dictionary of relation identifiers to be used by the policy
	 * 		  NOTE: Might be changed if populateRelIds is true.
	 * @param populateRelIds Flag to indicate weather or not should new relation identifiers be created.
	 * 		  NOTE: The newly created relation identifiers are of type String.
	 * 		  
	 * @return The policy object as described by the input stream
	 * @throws SAXException when the root element of the input stream is not "policy"
	 */
	public static Policy getPolicy(InputStream policyIS, Map<Object, Object> relationIdentifiers, Boolean populateRelIds) throws SAXException{
		RebacPolicyParser rpp = new RebacPolicyParser(policyIS, relationIdentifiers, populateRelIds);
		rpp.parse(true);
		
		return rpp.getPolicyObj();
	}

	/**	
	 * Parses the given policies input stream. The relationIdentifiers map is used while creating the policy objects. 
	 * The relation identifiers are matched through the key and the value in the relId tag in the input stream.<br/>
	 * EX: &lt;relId direction="forward"&gt;friend&lt;/relId&gr;<br/>
	 * The above example would match a relation identifier object in the map with with key "friend".<br/>
	 * If the key is not matched in the map and populateRelIds is true, then a new relation identifier object
	 * will be created and added to the map. NOTE: This object will be of type String.</br>
	 * If the key is not matched in the map and populateRelIds is false, then an exception will be thrown 
	 * @param policiesIS The input stream to parse
	 * @param relationIdentifiers The dictionary of relation identifiers to be used by the policy
	 * 		  NOTE: Might be changed if populateRelIds is true.
	 * @param populateRelIds Flag to indicate weather or not should new relation identifiers be created.
	 * 		  NOTE: The newly created relation identifiers are of type String.
	 * 		  
	 * @return The list of policy objects as described by the input stream
	 * @throws SAXException when the root element of the input stream is not "policies"
	 */
	public static List<Policy> getPolicies(InputStream policiesIS, Map<Object, Object> relationIdentifiers, Boolean populateRelIds) throws SAXException{
		RebacPolicyParser rpp = new RebacPolicyParser(policiesIS, relationIdentifiers, populateRelIds);
		rpp.parse(false);
		
		return rpp.getPoliciesObj();
	}
	
	private Policy getPolicyObj(){
		return this.policyObj;
	}
	
	private List<Policy> getPoliciesObj(){
		return this.policiesObj;
	}
	
	/**
	 * Parse the xml input stream.
	 * @throws SAXException 
	 */
	private void parse(Boolean isSinglePolicy) throws SAXException{
		
		/**********************************************************
		 * The xml file is now assumed to be well formed and valid
		 * against the schema
		 **********************************************************/
		// Parse the xml input stream
		try {
			
			// Get the schema
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			InputStream schemaIS = getClass().getResourceAsStream("/rebacPolicy.xsd");
			Schema schema = sf.newSchema(new StreamSource(schemaIS));	
			
			// Get the DOM parser
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			dbfactory.setSchema(schema);
			dbfactory.setNamespaceAware(true);
			DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
			Document doc = dbuilder.parse(xmlIS);
			// Normalize the root element
			doc.getDocumentElement().normalize();
			
			if (doc.getDocumentElement().getNodeName().contains("policies")){
				// The root element was "policies"
				
				if (isSinglePolicy){
					// Throw exception if "policy" root was expected
					throw new SAXException ("Expected root node to be \"policy\" but was \"policies\"");
				}
				
				policiesObj = new ArrayList<Policy>();
				parsePolicies(doc.getDocumentElement());
			} 
			else if (doc.getDocumentElement().getNodeName().contains("policy")){
				// The root element was "policy"
				
				if (!isSinglePolicy){
					// Throw exception if "policies" root was expected
					throw new SAXException ("Expected root node to be \"policies\" but was \"policy\"");
				}

				
				// Get the children nodes of the root element 
				NodeList nList = doc.getDocumentElement().getChildNodes();
				
				for (int i = 0;  i < nList.getLength(); i++){
					Node node = nList.item(i);
					// Loop through the children nodes of the root element, 
					// till you find a Element type node
					if (node.getNodeType() == Node.ELEMENT_NODE){
						// Parse the policy
						Element element = (Element) node;
						policyObj = parsePolicy(element);
					}
				}
			}
		} catch (SAXException e){
			throw e;
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Populates the policies field with the policies from the xml file
	 * @param element The root element of the xml file
	 */
	private void parsePolicies(Element element){
		// Get the children of the root element
		NodeList nodeList = element.getChildNodes();
		
		// Loop through all the children nodes
		for (int i = 0; i < nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			
			if (node.getNodeType() == Node.ELEMENT_NODE){
				// Find the Element type nodes: (<policy>)
				Element tempElement = (Element) node;
				
				// Get the children of the <policy> element
				NodeList nList = tempElement.getChildNodes();
				
				// Loop through all the children of the <policy> element
				for (int j = 0; j < nList.getLength(); j++){
					Node nNode = nList.item(j);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE){
						// Find the Element type node (represents some policy variant)
						Element eElement = (Element) nNode;
						// Parse the element representing the policy and 
						// add it to the policies list
						Policy tempPolicy = parsePolicy(eElement);
						policiesObj.add(tempPolicy);						
					}
				}
			}
		}
	}
	
	/**
	 * Recursively parses the policy where the root is the given element
	 * @param element The current element
	 * @return The policy created
	 */
	private Policy parsePolicy(Element element){
		Policy newPolicy = null;
		
		if(element.getNodeName().equals("true")){
			// element represents the "true" variant
			newPolicy = TrueImpl.getInstance();
		}
		else if (element.getNodeName().equals("false")){
			// element represents the "false" variant
			newPolicy = FalseImpl.getInstance();
		}
		else if (element.getNodeName().equals("variable")){	
			Object temp=null; 
			temp = new String (element.getFirstChild().getNodeValue());
			newPolicy = new VariableImpl(temp);
		}
		else if (element.getNodeName().equals("at")||element.getNodeName().equals("bind")){
			Policy policyA = null;
			Object variable = null;
			
			// Get the children nodes of element
			NodeList nodeList = element.getChildNodes();
			
			// Loop through the children nodes of element
			for (int i=0; i < nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE){
					
					Element tempElement = (Element) node;
					if (tempElement.getNodeName().equals("varID")){
						variable = new String (tempElement.getFirstChild().getNodeValue());
					}
					else {
						// Else the node represents the sub policy
						// Parse and set the sub policy
						policyA = parsePolicy(tempElement);
					}
				}	
			}	
			if (element.getNodeName().equals("at")){
				// If element represented the at variant, then create new At object
				newPolicy = new AtImpl(variable, policyA);
			}
			else {
				// Else element represented the bind variant, then create new Bind object
				newPolicy = new BindImpl(variable, policyA);
			}
		}
		else if (element.getNodeName().equals("and") || element.getNodeName().equals("or")){
			// element represents the "and" (Conjunction) or "or" (Disjunction) variant
			// The case is combined since both behave similarly
			
			// Initialize the sub policies
			Policy policyA = null;
			Policy policyB = null;
			
			// Get the children nodes of element
			NodeList nodeList = element.getChildNodes();
			
			for (int i=0; i < nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE){
					// Find the Element type nodes 
					Element tempElement = (Element) node;
					
					// First parse and set policyA, then parse and set policyB
					if (policyA == null){
						policyA = parsePolicy(tempElement);
					}
					else{
						policyB = parsePolicy(tempElement);
						break;
					}
				}
			}
			
			if (element.getNodeName().equals("and")){
				// If element represents "and" variant, then create new Conjunction object
				newPolicy = new ConjunctionImpl(policyA, policyB);
			}
			else {
				// Else element represents "or" variant, create new Disjunction object 
				newPolicy = new DisjunctionImpl(policyA, policyB);
			}
		}
		else if (element.getNodeName().equals("not")){
			// element represents "not" (negation) variant
			
			// Initialize the sub policy
			Policy policyA = null;
			
			// Get the children nodes of element
			NodeList nodeList = element.getChildNodes();
			
			// Loop through the children nodes of element
			for (int i=0; i < nodeList.getLength(); i++){
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE){
					// Find the Element type node
					Element tempElement = (Element) node;
					// Parse and set the sub policy
					policyA = parsePolicy(tempElement);
					break;
				}
			}
			// Create new Negation object
			newPolicy = new NegationImpl(policyA);
			
		}
		else if (element.getNodeName().equals("box") || element.getNodeName().equals("diamond")){
			// element represents the "box" or "diamond" variant
			// The case is combined since both behave similarly

			// Initialize the sub policy, relation identifier, and direction
			Policy policyA = null;
			Object relationIdentifier = null;
			Direction direction = null;
			
			// Get the children nodes of element
			NodeList nodeList = element.getChildNodes();
			
			// Loop through the children nodes of element
			for (int i = 0; i < nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE){
					// Find the Element type node
					Element tempElement = (Element) node;
					
					if (tempElement.getNodeName().equals("relId")){
						// If the node represents relation identifier
						
						// Set direction fields
						String dir = tempElement.getAttribute("direction");
						if (dir.equals("forward")){
							direction = Direction.FORWARD;
						}
						else if (dir.equals("backward")){
							direction = Direction.BACKWARD;
						}
						else if (dir.equals("either")){
							direction = Direction.EITHER;
						}
						
						// Parse and set the relation identifier object
						relationIdentifier = parseRelationIdentifier(tempElement);
					}
					else {
						// Else the node represents the sub policy
						// Parse and set the sub policy
						policyA = parsePolicy(tempElement);
					}
				}
			}
			
			if (element.getNodeName().equals("box")){
				// If element represented the Box variant, then create new Box object
				newPolicy = new BoxImpl(policyA, relationIdentifier, direction);
			}
			else {
				// Else element represented the Diamond variant, then create new Diamond object
				newPolicy = new DiamondImpl(policyA, relationIdentifier, direction);
			}
		}
		// Return the newly created policy
		return newPolicy;
	}
	
	/**
	 * Parses an element representing a Relation Identifier object
	 * @param element The element representing a relation identifier
	 * @return The relation identifier
	 */
	private Object parseRelationIdentifier(Element element){
		Object relationIdentifier = null;
		
		// Get the value of the first of the element
		// This value will be used as a key in the map representing the collection of
		// relation identifiers
		String key = element.getFirstChild().getNodeValue();
		
		
		if (populateRelIds){
			// Populate relation identifiers
			// Create relation identifiers as required
			// Note: The relation identifiers created will be of type String
			if (relationIdentifiers.containsKey(key)){
				// If the key already exists in the relation identifiers map
				// Then return the corresponding relation identifier object
				relationIdentifier = relationIdentifiers.get(key);
			} else {
				// Else, create the relation identifier object and add the 
				// key value pair in the relation identifiers map
				// and return the newly created relation identifier object
				relationIdentifier = new String(key);
				relationIdentifiers.put(key, relationIdentifier);
			}
		} else {
			// Work with the given collection of relation identifiers
			if (relationIdentifiers.containsKey(key)){
				// Search the relation identifiers map for the key and return the 
				// corresponding relation identifier object
				relationIdentifier = relationIdentifiers.get(key);
			} else {
				// Else if the key is not found in the relation identifiers map
				// Throw an exception
				throw new NoSuchElementException("Relation Identifier key " + key + " is not in the given map");
			}
		}
		return relationIdentifier;
	}
}
