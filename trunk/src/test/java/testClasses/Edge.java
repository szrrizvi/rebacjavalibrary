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
package testClasses;

/**
 * @author Syed Zain Rizvi
 * @author Mona Hosseinkhani
 */

/**
 * Represents a directed edge object for reading the GraphML file, created by yEd.
 */
public class Edge {

	private String id;			// id (chosen automatically in yEd)
	private Object relationIdentifier;	// relationIdentifier (identified through label in yEd)
	private Node source;			// source node
	private Node target;			// target node
	
	// Default constructor
	public Edge(){}
	
	/**
	 * Contructor to initialize fields.
	 * @param id The id (chosen automatically by yEd) for the Edge
	 * @param relationIdentifier The relation identifier (identified through label in yEd) 
	 *	  for the Edge
	 * @param source The source node
	 * @param target The target node
	 */
	public Edge(String id, Object relationIdentifier, Node source, Node target){
		// Set fields
		this.id = id;
		this.relationIdentifier = relationIdentifier;
		this.source = source;
		this.target = target;
	}

	// Setters and getters

	/**
	 * @return The Id
	 */
	public String getId(){
		return this.id;
	}
	
	/**
	 * @param id The Id to set
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * @return The relationIdentifier
	 */
	public Object getRelationIdentifier() {
		return relationIdentifier;
	}

	/**
	 * @param relationIdentifier The relation identifier to set
	 */
	public void setRelationIdentifier(Object relationIdentifier) {
		this.relationIdentifier = relationIdentifier;
	}

	/**
	 * @return The source node
	 */
	public Node getSource() {
		return source;
	}

	/**
	 * @param source The source node to set
	 */
	public void setSource(Node source) {
		this.source = source;
	}

	/**
	 * @return The target node
	 */
	public Node getTarget() {
		return target;
	}

	/**
	 * @param target The target node to set
	 */
	public void setTarget(Node target) {
		this.target = target;
	}
	
	/**
	 * @return A readible version of the Edge
	 */
	public String toString(){
		String str = "("+id + " " + relationIdentifier + " " + source + "->" + target  + ")";
		return str;
	}

	/**
	 * Calculates the hash code for the object
	 * @return The hash code for the object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((relationIdentifier == null) ? 0
						: relationIdentifier.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	/**
	 * Compares this object to the given object
	 * @param obj The object to compare
	 * @return true if and only if the given object matches this object, else false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)			// Compare pointers
			return true;
		if (obj == null)			// null obj
			return false;
		if (getClass() != obj.getClass())	// Compare class
			return false;
		Edge other = (Edge) obj;		// Cast obj into Edge type
		if (id == null) {			// Compare id
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		if (relationIdentifier == null) {	// Compare relation identifier
			if (other.getRelationIdentifier() != null)
				return false;
		} else if (!relationIdentifier.equals(other.getRelationIdentifier()))
			return false;
		if (source == null) {			// Compare source node
			if (other.getSource() != null)
				return false;
		} else if (!source.equals(other.getSource()))
			return false;
		if (target == null) {			// Compare target node
			if (other.getTarget() != null)
				return false;
		} else if (!target.equals(other.getTarget()))
			return false;
		return true;	// If reached here, return true
	}
}
