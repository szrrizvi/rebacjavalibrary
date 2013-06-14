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
package ca.ucalgary.ispia.rebac.util;

import java.util.Iterator;

/**
 * @author Syed Zain Rizvi
 */

/**
 * IterablePair. This class is used to combine two iterables.
 * @param <E>
 */

public class IterablePair<E> implements Iterable<E> {

	Iterable<E> iteA, iteB;
	
	/**
	 *  Constructor. Consumes the 2 iterables for this class.
	 * @param iteA
	 * @param iteB
	 */
	public IterablePair(Iterable<E> iteA, Iterable<E> iteB) {
		this.iteA = iteA;
		this.iteB = iteB;
	}
	
	/**
	 * Returns an iterator for itself.
	 */
	@Override
	public Iterator<E> iterator() {
		return new IteratorPair<E>(iteA.iterator(), iteB.iterator());
	}
	
	/**
	 * Contained iterator class. So that FrameIts.iterator() can be used multiple times.
	 *
	 * @param <E>
	 */
	private class IteratorPair<E> implements Iterator<E> {
		
		Iterator<E> itrA, itrB;
		
		/**
		 * Contructor; Assign the iterators
		 * @param itrA
		 * @param itrB
		 */
		public IteratorPair(Iterator<E> itrA, Iterator<E> itrB) {
			this.itrA = itrA;
			this.itrB = itrB;
		}
		
		/**
		 * Checks if the iterator has more elements
		 */
		public boolean hasNext() {
			return (itrA.hasNext() || itrB.hasNext());
		}
		
		/**
		 * Returns the next element in the iterator
		 */
		public E next() {
			if (itrA.hasNext()) {
				return itrA.next();
			} else {
				return itrB.next();
			}
		}
		
		/**
		 * Not supported
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}
