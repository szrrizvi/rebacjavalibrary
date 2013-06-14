/**
 * Copyright (c) 2013 Mona Loorak
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

import ca.ucalgary.ispia.rebac.At;
import ca.ucalgary.ispia.rebac.Policy;

public class AtImpl implements At{
	
	/**
	 * The policy to relate the accessor.
	 */
	public final Policy policy;
	/**
	 * the variable which is the name of the target vertex.
	 */
	public final Object variable;
	
	/**
	 * Initializes the field members
	 * @param policy: the policy to relate the accessor
	 * @param variabel: the name of the target node
	 */
	public AtImpl (Object variable, Policy policy){
		this.policy=policy;
		this.variable=variable;
	}

	@Override
	public Policy getPolicy(){
		return this.policy;
	}

	@Override
	public Object getVariable(){
		return this.variable;
	}

	@Override
	public String toString() {
		String str="";
		str=str+ "(@";
		
		str=str+variable;
		str=str+policy;
		str=str+")";
		
		return str;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + "At".hashCode();
		result = prime * result + ((variable == null)? 0 : variable.hashCode());
		result = prime * result + ((policy == null) ? 0 : policy.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;	// Compare pointers
		}
		if (obj == null) {
			return false;		// False if obj is null
		}
		if (!(obj instanceof AtImpl)) {
			return false;		// obj and this are not of the same instance
		}
		AtImpl other = (AtImpl) obj;
		
		if (policy == null) {
			if (other.policy != null) {
				return false;		// False if contained policy is null, and obj's policy is not null
			}
		} else if (!policy.equals(other.policy)) {
			return false;			// False if contained policy does not match
		}
		if (variable == null){
			if (other.variable != null){
				return false;		// False if contained variable is null, and obj's variable is not null
			}
		} else if (!variable.equals(other.variable)) {
			return false;			// False if variable does not match
		}
		return true;				// if reached here, true
	}
}
