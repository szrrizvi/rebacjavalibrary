package ca.ucalgary.ispia.rebac;

/**
 * @author Syed Zain Rizvi
 */

/**
 * Represents the super class for Rebac Policy variants. The purpose of this
 * interface is to act as a marker.
 */
public interface Policy {

	/**
	 * Indicates where some other object is "equal" to this one.
	 * Each concrete policy variant class is required to override the equals method.
	 * NOTE: The equals method is always inherited by the java.lang.Object superclass.
	 * @param obj The objecy to check for equality against
	 * @return true if this object is the same as the obj argument; false otherwise
	 */
	public boolean equals(Object obj);
	
	/**
	 * Returns the hash code value of this object.
	 * NOTE: Each concrete policy variant class is required to override the hashCode method.
	 * NOTE: The hashCode method is always inherited by the java.lang.Object superclass. 
	 * @return The hash code of this object
	 */
	public int hashCode();
	
	/**
	 * Prints a string representation of this object.
	 * NOTE:Each concrete policy variant class is required to override the toString method.
	 * NOTE: The toString method is always inherited by the java.lang.Object superclass,
	 * but must of overwritten.
	 * @return A human readable version of this object
	 */
	public String toString();
}
