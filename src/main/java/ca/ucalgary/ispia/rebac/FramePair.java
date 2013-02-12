package ca.ucalgary.ispia.rebac;

import ca.ucalgary.ispia.rebac.util.IterablePair;

/**
 * @author Syed Zain Rizvi
 */

/**
 * This class is used to combine frames. 
 * NOTE: frameA or frameB could be of type FrameTuple as well.
 */

public class FramePair implements Frame{

	Frame frameA, frameB;
	
	@Override
	public Iterable findNeighbours(Object vertex, Object relationIdentifier, Direction direction){
			
		Iterable iteA = frameA.findNeighbours(vertex, relationIdentifier, direction);
		Iterable iteB = frameB.findNeighbours(vertex, relationIdentifier, direction);
		
		return new IterablePair(iteA, iteB);
	}
}
