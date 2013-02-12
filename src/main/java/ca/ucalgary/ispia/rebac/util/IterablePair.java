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
