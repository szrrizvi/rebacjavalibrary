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
 * @author Mona
 */
/**
 * A generic relationship between three objects/values.
 * @param <F> Type of first object/value.
 * @param <S> Type of second object/value.
 * @param <T> Type of third object/value.
 */

// Used by the model checker

public class Triple<F, S, T> {
		
	/**
	 * The first object/value.
	 */
	public final F first;
	/**
	 * The second object/value.
	 */
	public final S second;
	/**
	 * The third object/Value.
	 */
	public final T third;
	
	/**
	 * Initializes the fields.
	 * @param first The first object/value.
	 * @param second The second object/value.
	 * @param third The third object/value
	 */
	public Triple(F first, S second, T third){
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + "Triple".hashCode();
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		result = prime * result + ((third == null) ? 0 : third.hashCode());
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
		if (!(obj instanceof Triple)) {
			return false;		// False if obj is not of same instance
		}
		Triple<?,?,?> other = (Triple<?,?,?>) obj;
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
		if (third == null) {
			if (other.third != null) {
				return false;	// False if third is null and obj's third is not null
			}
		} else if (!third.equals(other.third)) {
			return false;		// False if third does not match
		}
		return true;			// If reached here, true
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Triple [first=" + first + ", second=" + second + ", third="+ third +"]";
	}


}
