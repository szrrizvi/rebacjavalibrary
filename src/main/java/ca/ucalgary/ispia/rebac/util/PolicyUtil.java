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


import java.util.HashSet;
import java.util.Set;

import ca.ucalgary.ispia.rebac.Direction;
import ca.ucalgary.ispia.rebac.Policy;
import ca.ucalgary.ispia.rebac.impl.AtImpl;
import ca.ucalgary.ispia.rebac.impl.BindImpl;
import ca.ucalgary.ispia.rebac.impl.BoxImpl;
import ca.ucalgary.ispia.rebac.impl.ConjunctionImpl;
import ca.ucalgary.ispia.rebac.impl.DiamondImpl;
import ca.ucalgary.ispia.rebac.impl.DisjunctionImpl;
import ca.ucalgary.ispia.rebac.impl.FalseImpl;
import ca.ucalgary.ispia.rebac.impl.NegationImpl;
import ca.ucalgary.ispia.rebac.impl.RequestorImpl;
import ca.ucalgary.ispia.rebac.impl.ResourceImpl;
import ca.ucalgary.ispia.rebac.impl.TrueImpl;
import ca.ucalgary.ispia.rebac.impl.VariableImpl;
import ca.ucalgary.ispia.rebac.impl.OwnerImpl;
import ca.ucalgary.ispia.rebac.At;
import ca.ucalgary.ispia.rebac.Bind;
import ca.ucalgary.ispia.rebac.Box;
import ca.ucalgary.ispia.rebac.Conjunction;
import ca.ucalgary.ispia.rebac.Diamond;
import ca.ucalgary.ispia.rebac.Disjunction;
import ca.ucalgary.ispia.rebac.False;
import ca.ucalgary.ispia.rebac.Negation;
import ca.ucalgary.ispia.rebac.Requestor;
import ca.ucalgary.ispia.rebac.True;
import ca.ucalgary.ispia.rebac.Variable;
import ca.ucalgary.ispia.rebac.Owner;



import ca.ucalgary.ispia.rebac.util.Constants;


/**

 * @author Syed Zain Rizvi
 * @author Mona Hosseinkhani

 */



/**

 * Offers utility methods for Policies

 */

public class PolicyUtil {

	

	/**

	 * Consumes a policy and translates it to a policy only composed of primitive

	 * operators. NOTE: If the given policy is already only composed of primitive

	 * operators, it is returned as is.

	 * Primitive Operators: {@link Variable}, {@link Diamond}, {@link Conjunction},

	 * {@link Negation}, {@link True} , {@link False}, {@link At} and {@link Bind}

	 * @param tPolicy The policy to be translated.

	 * @return A policy composed of only primitive operators.

	 */

	public static Policy translate(Policy tPolicy){

		Policy translatedPolicy;



		if (tPolicy instanceof VariableImpl){

			//Already primitive.

			translatedPolicy = tPolicy;

		}

		else if (tPolicy instanceof OwnerImpl){
		
			//Translate the Owner variant to a variable
			translatedPolicy = new VariableImpl(Constants.owner);
			
		}
		else if (tPolicy instanceof RequestorImpl){
		
			//Translate the Requestor variant to a variable
			translatedPolicy = new VariableImpl(Constants.requestor);
		}
		else if (tPolicy instanceof ResourceImpl){
			//Translate the Resource variant to ta variable
			translatedPolicy = new VariableImpl(Constants.resource);
		}

		else if (tPolicy == FalseImpl.getInstance()){

			// Already primitive

			translatedPolicy = tPolicy;

		}

		else if (tPolicy ==TrueImpl.getInstance()){

			// Already primitive

			translatedPolicy = tPolicy;

		}

		

		else if(tPolicy instanceof NegationImpl){

			// Already primitive. Recurse on contained policy.

			NegationImpl temp = (NegationImpl) tPolicy;

			// Get contained fields

			Policy tempA = temp.getPolicy();

			// Translate contained policy

			tempA = translate(tempA);

			// Create translated policy

			translatedPolicy = new NegationImpl (tempA);

		} 

		

		else if (tPolicy instanceof ConjunctionImpl){

			// Already primitive. Recurse on contained policies.

			

			ConjunctionImpl temp = (ConjunctionImpl) tPolicy;

			// Get contained fields

			Policy tempA = temp.getPolicyA();

			Policy tempB = temp.getPolicyB();

			// Translate contained fields

			tempA = translate(tempA);

			tempB = translate(tempB);

			// Create translated policy

			translatedPolicy = new ConjunctionImpl(tempA, tempB);

		}

		

		else if (tPolicy instanceof DisjunctionImpl){

			// Disjunction(policyA, policyB) =

			//	Negation(Conjunction((Negation(policyA)), (Negation(policyB))))

			

			DisjunctionImpl temp = (DisjunctionImpl) tPolicy;

			// Get contained fields

			Policy tempA = temp.getPolicyA();

			Policy tempB = temp.getPolicyB();

			// Translate contained policies

			tempA = new NegationImpl (translate(tempA));

			tempB = new NegationImpl (translate(tempB));

			// Create translated policy

			Policy tempC = new ConjunctionImpl(tempA, tempB);

			translatedPolicy = new NegationImpl (tempC);

			

		}

		

		else if (tPolicy instanceof BoxImpl){

			// Box(policy) = Negation(Diamond(Negation(policy)))



			BoxImpl temp = (BoxImpl) tPolicy;

			

			// Get contained fields

			Policy tempA = temp.getPolicy();

			Object relationID = temp.getRelationIdentifier();

			Direction direction = temp.getDirection();

			// Translate contained policy

			tempA = new NegationImpl (translate(tempA));

			// Create translated policy

			Policy tempB = new DiamondImpl(tempA, relationID, direction);		

			translatedPolicy = new NegationImpl (tempB);

		}

		

		else if (tPolicy instanceof DiamondImpl){

			// Already primitive. Recurse on contained policy.

			

			DiamondImpl temp = (DiamondImpl) tPolicy;

			// Get contained fields

			Policy tempA = temp.getPolicy();

			Object relationID = temp.getRelationIdentifier();

			Direction direction = temp.getDirection();

			// Translate contained policy

			tempA = translate(tempA);

			// Create translated policy

			translatedPolicy = new DiamondImpl(tempA, relationID, direction);

		}

		

		else if (tPolicy instanceof BindImpl){

			//Already primitive. Recurse on contained policy

			

			BindImpl temp= (BindImpl) tPolicy;

			Policy tempA = temp.getPolicy();

			Object var = temp.getVariable();

			

			//translate contained policy

			tempA=translate(tempA);

			// Create translated policy

			translatedPolicy = new BindImpl(var, tempA);

		}

		

		else if (tPolicy instanceof AtImpl){

			//Already primitive. Recurse on contained policy

			

			AtImpl temp = (AtImpl) tPolicy;

			Policy tempA = temp.getPolicy();

			Object var = temp.getVariable();

			

			//translate contained policy

			tempA=translate(tempA);

			// Create translated policy

			translatedPolicy = new AtImpl(var, tempA);

		}

		

		else {

			// Else (not a supported policy variant), throw exception

			throw new IllegalArgumentException("The given policy is not supported by the Translate method: " + tPolicy);

		}

		

		return translatedPolicy;

	}

	


	public static Set<Object> findFreeVars(Policy policy, Set<Object> free_vars){

		

		if (policy instanceof Variable) {

			Variable temp = (Variable) policy;

			free_vars.add(temp.getVariable());

		}

		else if (policy instanceof False){

			//nothing added

		}

		else if (policy instanceof True){

			//nothing added

		}
		
		else if (policy instanceof Requestor){
			//noting added
		}

		else if (policy instanceof Owner){
			//nothing added
		}
		

		else if(policy instanceof Negation){

			Negation temp = (Negation) policy;

			findFreeVars(temp.getPolicy(), free_vars);

		} 

		

		else if (policy instanceof Conjunction){

			Conjunction temp = (Conjunction) policy;

			

			Set<Object> temp_setA = findFreeVars(temp.getPolicyA(), new HashSet<Object>());

			Set<Object> temp_setB = findFreeVars(temp.getPolicyB(), new HashSet<Object>());

			free_vars.addAll(temp_setA);

			free_vars.addAll(temp_setB);

		}

		

		else if (policy instanceof Disjunction){

			Disjunction temp = (Disjunction) policy;

			

			Set<Object> temp_setA = findFreeVars(temp.getPolicyA(), new HashSet<Object>());

			Set<Object> temp_setB = findFreeVars(temp.getPolicyB(), new HashSet<Object>());

			free_vars.addAll(temp_setA);

			free_vars.addAll(temp_setB);

		}

		

		else if (policy instanceof Box){

			Box temp = (Box) policy;

			findFreeVars(temp.getPolicy(), free_vars);

		}

		

		else if (policy instanceof Diamond){

			Diamond temp = (Diamond) policy;

			free_vars = findFreeVars(temp.getPolicy(), free_vars);

		}

		

		else if (policy instanceof Bind){

			Bind temp = (Bind) policy;

			findFreeVars(temp.getPolicy(), free_vars);

			free_vars.remove(temp.getVariable());

		}

		

		else if (policy instanceof At){

			At temp = (At) policy;

			findFreeVars(temp.getPolicy(), free_vars);

			free_vars.add(temp.getVariable());

		}

		return free_vars;

	}

}

