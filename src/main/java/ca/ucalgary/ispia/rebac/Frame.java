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
 * Provides a representation-independent way for model checking algorithm to
 * interact with the protection state (i.e., the social networks and the
 * context hierarchy).
 */
public interface Frame {
	
	/**
	 * Finds all the specific type neighbours of the given vertex, in the effective
	 * social network of the context.<br>
	 * <b>Note:</b> It is up to the programmer to establish contexts.
	 * @param vertex The vertex to find the neighbours of.
	 * @param relationIdentifier The relation identifier.
	 * @return Specific type neighbours of the given vertex in the effective social
	 * network.
	 */
	@SuppressWarnings("rawtypes")
	public Iterable findNeighbours(Object vertex, Object relationIdentifier, Direction direction);
}
