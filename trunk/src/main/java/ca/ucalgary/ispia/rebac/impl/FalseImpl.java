package ca.ucalgary.ispia.rebac.impl;

import ca.ucalgary.ispia.rebac.False;

/**
 * @author Syed Zain Rizvi
 */

/**
 * A concrete implementation of {@link False}<p>
 * This implementation follows the singleton design pattern.
 * @see ca.ucalgary.ispia.rebac.False
 */
public final class FalseImpl implements False {
	
	private static final FalseImpl theInstance = new FalseImpl();
	private static final int hashCode;
	/**
	* Private constructor prevents instantiation from other classes.
	*/
	private FalseImpl(){}
	
	static {
		final int prime = 31;
		int result = 1;
		hashCode = prime * result + "False".hashCode();				
	}
	
	/**
	 * Returns the instance of the object.
	 * @return The instance
	 */
	public static FalseImpl getInstance(){
		
		return theInstance;
	}
	
	/**
	 * Prints out a human readable version of this object.
	 * @return A human readable version of this object
	 * @see ca.ucalgary.ispia.rebac.Policy#toString()
	 */	
	@Override
	public String toString(){
		return ("False");
	}
	
	/**
	 * Returns the hash code of this object
	 * @return The hash code of this object
	 * @see ca.ucalgary.ispia.rebac.Policy#hashCode()
	 */
	@Override
	public int hashCode(){

		return hashCode;
	}
	
	/**
	 * Checks if this object is equal to the given object.
	 * @return True if this object is equal to the given object, else false.
	 * @see ca.ucalgary.ispia.rebac.Policy#equals(Object)
	 */
	@Override
	public boolean equals(Object obj){
		// False if obj is null
		if (obj == null) return false;
		// If obj is False object return true, else false.
		// False follows Singleton design pattern
		return (this == obj);
	}
}
