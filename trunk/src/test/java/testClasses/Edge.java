package testClasses;

/**
 * @author Syed Zain Rizvi
 */

/**
 * Represents an Edge object for reading the GraphML file, created by yEd.
 */
public class Edge {

	private String id;		// id (chosen automatically in yEd)
	private Object relationIdentifier;	// relationIdentifier (identified through label in yEd)
	private Node source;	// source node
	private Node target;	// target node
	
	public Edge(){}
	
	public Edge(String id, Object relationIdentifier, Node source, Node target){
		this.id = id;
		this.relationIdentifier = relationIdentifier;
		this.source = source;
		this.target = target;
	}

	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public Object getRelationIdentifier() {
		return relationIdentifier;
	}

	public void setRelationIdentifier(Object relationIdentifier) {
		this.relationIdentifier = relationIdentifier;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}
	
	public String toString(){
		String str = "("+id + " " + relationIdentifier + " " + source + "->" + target  + ")";
		return str;
	}

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (relationIdentifier == null) {
			if (other.relationIdentifier != null)
				return false;
		} else if (!relationIdentifier.equals(other.relationIdentifier))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}
}
