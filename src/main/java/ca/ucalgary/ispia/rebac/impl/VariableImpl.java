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
package ca.ucalgary.ispia.rebac.impl;

import ca.ucalgary.ispia.rebac.Variable;

/**
 * @author Mona Hosseinkhani
 */

/**
 * A concrete implementation of {@link Virable}.
 * @see ca.ucalgary.ispia.rebac.Variable
 */
public class VariableImpl implements Variable {

	public final Object variable;
	//public final Object vertex;
	
	public VariableImpl(Object variable/*, Object vertex*/){
		this.variable=variable;
		//this.vertex=vertex;
	}
	
	@Override
	public Object getVariable() {
		return this.variable;
	}
	
	@Override
	public String toString() {
		return variable.toString();
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + "variable".hashCode();
		result = prime * result + ((variable == null)? 0 : variable.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;	// Compare pointers
		}
		if (obj == null){
			return false;
		}
		if (!(obj instanceof Variable)){
			return false;
		}
		Variable other = (Variable) obj;
		if (variable == null){
			if (other.getVariable() != null){
				return false;		// False if contained proposition is null, and obj's proposition is not null
			}
		} else if (!(variable.equals(other.getVariable()))) {
			return false;			// False if proposition does not match
		}
		return true;
	}

}
