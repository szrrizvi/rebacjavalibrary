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

/**
 * @author Syed Zain Rizvi
 * @author Mona Hosseinkhani
 */

/**
 * Represents a Node object for reading the GraphML file, created by yEd.
 */

public class Node {
	
	private String id;		// id (chosen automatically in yEd)
	private String name;		// name (label in yEd)
	
	/**
	 * Default constructor
	 */
	public Node (){};
	
	/**
	 * Constructor to initialize fields
	 * @param id The node id (chosen automatically by yEd)
	 * @param name The node name (identified through label in yEd)
	 */
	public Node (String id, String name){
		// Set fields.
		this.id = id;
		this.name = name;
	}

	/**
	 * Constructor for Node with an ID but no name
	 * @ id The node id (chosen automatically by yEd)
	 */
	public Node (String id){
		// Set id
		this.id = id;
	}

	// Setters and getters
	
	/**
	 * @return The node id
	 */
	public String getId(){
		return this.id;
	}
	
	/**
	 * @param id The node id to set
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * @return The node name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * @param name The node name to set
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @return A readable version of the node
	 */
	public String toString(){
		String str = "(" + id + " " + name + ")";
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Compares this object to the given object
	 * @param obj The object to compare
	 * @return true if and only if the given object matches this object, else false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)		// compare pointers
			return true;
		if (obj == null)		// null obj
			return false;
		if (getClass() != obj.getClass())	// compare classes
			return false;
		Node other = (Node) obj;
		if (id == null) {		// compare id
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {		// compare name
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;		// if reached here, return true
	}
}
