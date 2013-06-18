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

package ca.ucalgary.ispia.rebac;

/**
 * @author Syed Zain Rizvi
 */

/**
 *  Box variant for the interface {@link Policy}. Asserts that every individual
 *  related to the owner via a certain relationship (identified by the 
 *  relation identifier) is also related to the requestor in a manner specified
 *  by a certain policy (identified by the contained policy). <p>
 *  Derived from the variants {@link Negation} and {@link Diamond}.
 */
public interface Box extends Policy {

	/**
	 * @return The contained policy
	 */
	public Policy getPolicy();
	
	/**
	 * @return The object representing the relation identifier
	 */
	public Object getRelationIdentifier();
	
	/**
	 * @return The direction specified by this object
	 */
	public Direction getDirection();
}
