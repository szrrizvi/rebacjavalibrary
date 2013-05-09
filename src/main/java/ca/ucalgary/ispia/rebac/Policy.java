/**
 * Copyright (c) 2013 Syed Zain Rizvi
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
