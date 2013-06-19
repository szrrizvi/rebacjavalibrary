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

import org.junit.Test;
import org.junit.Assert;

import ca.ucalgary.ispia.rebac.Direction;
import ca.ucalgary.ispia.rebac.Policy;
import ca.ucalgary.ispia.rebac.impl.*;
import ca.ucalgary.ispia.rebac.util.PolicyUtil;

/**
 * @author Syed Zain Rizvi
 */

/**
 * Test cases for ca.ucalgary.ispia.rebac.util.PolicyUtil
 */
public class PolicyUtilTest {

	// Testing the Translate method

	/**
	 * Test translate method for Variable variant
	 */
	@Test
	public void testTranslateVariable(){
		// Create policy: (Variable v)
		Policy policy = new VariableImpl("v");
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);
		// Primitive variant, translation should be same as initial
		Assert.assertTrue(policy.equals(translate));
	}

	/**
	 * Test translate method for False variant
	 */
	@Test
	public void testTranslateFalse(){
		// Create policy: False
		Policy policy = FalseImpl.getInstance();
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);
		// Primitive variant, translation should be same as initial
		Assert.assertTrue(policy.equals(translate));
	}

	/**
	 * Test translate method for True variant
	 */
	@Test
	public void testTranslateTrue(){
		// Create policy: True
		Policy policy = TrueImpl.getInstance();
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);
		// Primitive variant, translation should be same as initial
		Assert.assertTrue(policy.equals(translate));
	}

	/**
	 * Test translate method for Negation variant
	 */
	@Test
	public void testTranslateNegation(){
		// Creat policy: (not True)
		Policy policy = new NegationImpl(TrueImpl.getInstance());
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);
		// Primitive policy, translation should be same as initial
		Assert.assertTrue(policy.equals(translate));
	}

	/**
	 * Test translate method for Conjunction variant
	 */
	@Test
	public void testTranslateConjunction(){
		// Create policy: (True and False)
		Policy policy = new ConjunctionImpl(TrueImpl.getInstance(), FalseImpl.getInstance());
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);
		// Primitive policy, translation should be same as initial
		Assert.assertTrue(policy.equals(translate));
	}

	/**
	 * Test translation method for Disjunction variant
	 */
	@Test
	public void testTranslateDisjunction(){

		// Create policy: (True or False)
		Policy policy = new DisjunctionImpl(TrueImpl.getInstance(), FalseImpl.getInstance());
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);

		// Create expected translated policy
		// Disjunction(True, Flase) =
		//	Negation(Conjunction((Negation(True)), (Negation(False))))
		Policy p1 = new NegationImpl(TrueImpl.getInstance());
		Policy p2 = new NegationImpl(FalseImpl.getInstance());
		Policy p3 = new ConjunctionImpl(p1, p2);
		Policy expected = new NegationImpl(p3);

		Assert.assertTrue(expected.equals(translate));				
	}

	/**
	 * Test translation method for Box variant
	 */
	@Test
	public void testTranslateBox(){
		// Create policy: ([i] True)
		Direction direction = Direction.FORWARD;
		Object relationIdentifier = "i";
		Policy policy = new BoxImpl(TrueImpl.getInstance(), relationIdentifier, direction);
		// Call translate mthod
		Policy translate = PolicyUtil.translate(policy);

		// Create expected translated policy
		// Box(True, relationIdentifier, direction) = 
		//	Negation(Diamond(Negation(True), relationIdentifier, direction))
		Policy p1 = new NegationImpl(TrueImpl.getInstance());
		Policy p2 = new DiamondImpl(p1, relationIdentifier, direction);
		Policy expected = new NegationImpl(p2);

		Assert.assertTrue(expected.equals(translate));				
	}

	/**
	 * Test translate method for Diamond variant
	 */
	@Test
	public void testTranslateDiamond(){
		// Create policy: (<i> True)
		Direction direction = Direction.FORWARD;
		Object relationIdentifier = "i";
		Policy policy = new DiamondImpl(TrueImpl.getInstance(), relationIdentifier, direction);
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);
		// Primitive policy, translation should be same as initial
		Assert.assertTrue(policy.equals(translate));
	}

	/**
	 * Test translate method for Bind variant
	 */
	@Test
	public void testTranslateBind(){
		// Create policy (!v True)
		Object variable = "v";
		Policy policy = new BindImpl(variable, TrueImpl.getInstance());
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);
		// Primitive policy, translation should be same as initial
		Assert.assertTrue(policy.equals(translate));
	}

	/**
	 * Test translate method for At variant
	 */
	@Test
	public void testTranslateAt(){
		// Create policy: (@v True)
		Object variable = "v";
		Policy policy = new AtImpl(variable, TrueImpl.getInstance());
		// Call translate method
		Policy translate = PolicyUtil.translate(policy);
		// Primitive policy, translation should be same as initial
		Assert.assertTrue(policy.equals(translate));
	}

	/**
	 * Test translate method for recursive translation
	 * Translation required at more than the first level.
	 */
	@Test
	public void testTranslateRecursive(){
		// Declare extra fields
		Object v = "v";
		Direction direction = Direction.FORWARD;
		Object relationIdentifier = "i";		

		// Create policy
		/*
		(((not ([i] True)) and (<i> False)) or ((!v True)) or (@v variable)))
		*/

		// Create policy in parts

		Policy t = TrueImpl.getInstance();
		Policy f = FalseImpl.getInstance();
		Policy variable = new VariableImpl(v);

		// at = (@v variable)
		Policy at = new AtImpl(v, variable);
		// bind = (!v True)
		Policy bind = new BindImpl(v, t);
		// diamond = (<i> False)
		Policy diamond = new DiamondImpl(f, relationIdentifier, direction);
		// box = ([i] True)
		Policy box = new BoxImpl(t, relationIdentifier, direction);
		// not = (not ([i] True))
		Policy not = new NegationImpl(box);
		// and = ((not ([i] True)) and (<i> False))
		Policy and = new ConjunctionImpl(not, diamond);
		// or1 = ((!v True) or (@v variable))
		Policy or1 = new DisjunctionImpl(bind, at);
		// or2 = ((and) or (or1))
		Policy or2 = new DisjunctionImpl(and, or1);

		// Create expected translated policy
		/* 
		(not ((not ((not (not (<i> (not True)))) and (<i> False))) and 
			(not (not ((not (!v True)) and (not (@v variable)))))))
		*/

		// Create policy in parts

		// t4 = (not (not (<i> (not True))))
		Policy t1 = new NegationImpl(t);
		Policy t2 = new DiamondImpl(t1, relationIdentifier, direction);
		Policy t3 = new NegationImpl(t2);
		Policy t4 = new NegationImpl(t3);

		// t5 = (<i> False)
		Policy t5 = new DiamondImpl(f, relationIdentifier, direction);

		// t7 = (not ((t4) and (t5)))
		Policy t6 = new ConjunctionImpl(t4, t5);
		Policy t7 = new NegationImpl(t6);

		// t9 = (not (!v True))
		Policy t8 = new BindImpl(v, t);
		Policy t9 = new NegationImpl(t8);
	
		// t12 = (not (@v variable)
		Policy t10 = new VariableImpl(v);
		Policy t11 = new AtImpl(v, t10);
		Policy t12 = new NegationImpl(t11);

		// t15 = (not(not ((t9) and (t2))))
		Policy t13 = new ConjunctionImpl(t9, t12);
		Policy t14 = new NegationImpl(t13);
		Policy t15 = new NegationImpl(t14);

		// expected = not ((t7) and (t15))
		Policy t16 = new ConjunctionImpl(t7, t15);
		Policy expected = new NegationImpl(t16);

		// Call translate method
		Policy translate = PolicyUtil.translate(or2);
		Assert.assertTrue(expected.equals(translate));
	}
}
