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

package ca.ucalgary.ispia.rebac.util;


/**
 * @author Syed Zain Rizvi
 */

/**
 * A generic relationship between two objects/values.
 * @param <F> Type of first object/value.
 * @param <S> Type of second object/value.
 */

// Used for extend

public class Pair<F, S> {
		
	/**
	 * The first object/value.
	 */
	public final F first;
	/**
	 * The second object/value.
	 */
	public final S second;
	
	/**
	 * Initializes the fields.
	 * @param first The first object/value.
	 * @param second The second object/value.
	 */
	public Pair(F first, S second){
		this.first = first;
		this.second = second;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + "Pair".hashCode();
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;		// Compare pointers
		}
		if (obj == null) {
			return false;		// False if obj is null
		}
		if (!(obj instanceof Pair)) {
			return false;		// False if obj is not of same instance
		}
		Pair<?,?> other = (Pair<?,?>) obj;
		if (first == null) {
			if (other.first != null) {
				return false;	// False if first is null and obj's first is not null
			}
		} else if (!first.equals(other.first)) {
			return false;		// False is first does not match
		}
		if (second == null) {
			if (other.second != null) {
				return false;	// False if second is null and obj's second is not null
			}
		} else if (!second.equals(other.second)) {
			return false;		// False if second does not match
		}
		return true;			// If reached here, true
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}


}
