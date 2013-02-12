package ca.ucalgary.ispia.rebac.impl;

import ca.ucalgary.ispia.rebac.True;

/**
 * @author Syed Zain Rizvi
 */

/**
 * A concrete implementation of {@link True}.<p>
 * This implementation follows the singleton design pattern
 * @see ca.ucalgary.ispia.rebac.True
 */
public final class TrueImpl implements True {

	private static final TrueImpl theInstance = new TrueImpl();
	private static final int hashCode;
	
	/**
	* Private constructor prevents instantiation from other classes.
	*/
	private TrueImpl(){}
	
	static{
		final int prime = 31;
		int result = 1;
		hashCode = prime * result + "True".hashCode();
	}
	
	/**
	 * Returns the instance of the object.
	 * @return The instance
	 */
	public static TrueImpl getInstance(){
		
		return theInstance;
	}
	
	/**
	 * Prints out a human readable version of this object.
	 * @return A human readable version of this object
	 * @see ca.ucalgary.ispia.rebac.Policy#toString()
	 */	
	@Override
	public String toString(){
		return ("True");
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
		// False if o is null
		if (obj == null) return false;
		// If obj is True object return true, else false.
		// True follows Singleton design pattern
		return (this == obj);
	}
}
