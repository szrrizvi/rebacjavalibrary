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
package ca.ucalgary.ispia.rebac;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Mona
 */

/**
 * Represents the valuation function for Hybrid logic
 */
public class Environment {
	
	/**
	 * variable : the first element in the valuation function.
	 */
	private Object variable;
	/**
	 * value : The second element in the valuation function.
	 */
	private Object value;
	/**
	 * rest : the valuation function exist before the new pair is added.
	 */
	private Environment rest;
	
	/**
	 * Initializing parameters.
	 * @param variable
	 * @param value
	 * @param rest
	 */
	public Environment(Object variable, Object value, Environment rest){
		this.variable = variable;
		this.value = value;
		this.rest = rest;
	}
	
	/**
	 * returns a new environment using the variable, value and the previous environment.
	 * environment parameter must be sorted in decreasing order and has no duplication.
	 * @param variable : the variable to be added in the new environment
	 * @param value : value of the variable
	 * @param environment : the previous environment.
	 */
	public static Environment insert (Object variable, Object value, Environment environment) {
		if (environment == null) {
			return new Environment(variable, value, environment);
		}
		else {
			Object tVar = environment.variable;
			Object tVal = environment.value;
			Environment tRest = environment.rest;
			
			if (variable.equals(tVar))
				return new Environment(variable, value, tRest);
			//if (variable < rest_var)
			else if (variable.toString().compareTo(tVar.toString()) < 0)
				return new Environment(variable, value, environment);
			else /* variable > rest_var*/ 
				return new Environment(tVar, tVal, insert(variable, value, tRest));
		}
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result =1;
		result = prime * result + "environment".hashCode();
		result = prime * result + ((variable == null)? 0 : variable.hashCode());
		result = prime * result + ((value == null)? 0 : value.hashCode());
		result = prime * result + ((rest == null)? 0 : rest.hashCode());
		return result;
	}
	
	@Override
	public boolean equals (Object obj) {
		if (this == obj) {
			return true;			// Compare pointers
		}
		if (obj == null) {
			return false;			// False if obj is null
		}
		if (!(obj instanceof Environment)) {
			return false;			// False is obj is not of same instance
		}
		
		Environment other = (Environment) obj;
		
		if (variable == null){
			if (other.variable != null){
				return false;
			}
		}
		else if (! variable.equals(other.variable)) {
			return false;
		}
		if (value == null){
			if (other.value != null){
				return false;
			}
		}
		else if (! value.equals(other.value)) {
			return false;
		}
		if (rest == null){
			if (other.rest != null){
				return false;
			}
		}
		else if (! rest.equals(other.rest)) {
			return false;
		}
		return true;
	}
	
	/**
	 * apply function on the valuation function
	 * @param env: the environment.
	 * @param var: The variable element of the valuation function
	 * @return null if env is null and otherwise returns the value of the environment that
	 * has the same object for variable as var 
	 */
	public static Object apply (Environment env, Object var){
		if (env == null) {
			return null;
		} 
		else {
			if (env.variable.equals(var)){
				return env.value;
			}
			else {
				return apply(env.rest, var);
			}
			}
		}
	
	/**
	 * gets all the variables in the input environment env and saves them in an arrayList
	 * @param env
	 * @param variables
	 * @return arrayList of all the variables in env
	 */
	public static List get_variables (Environment env, List variables) {
		if (env == null) {
			return variables;
		}
		else {
			variables.add(env.variable);
			return get_variables(env.rest, variables);
		}
	}
	
	/**
	 * projects variables other than vars from env.
	 * env must be sorted and has no duplicate
	 * vars must be the subset of dom(env)
	 * @param env : environment
	 * @param vars : vars that should not be projected from the env.
	 * @return : An env containing just the variables encluded in vars.
	 */
	public static Environment project(Environment env, Set vars) {
		//assert (env.get_variables(env, new ArrayList<Object>()).containsAll(vars));
		if (env == null)
			return null;
		else {
			Object tVar = env.variable;
			Object tVal = env.value;
			Environment tRest = env.rest;
			
			if (vars.contains(tVar)){
				//vars.remove(tVar);
				return new Environment(tVar, tVal, project(tRest, vars));
			}
			else // !(vars.contains(tVar))
				return project(tRest, vars);
		}
	}
	

	@Override
	public String toString(){
		String s = new String();
		s = s + variable + ",";
		s = s + value + ", ";
		s = s + rest;
		return s;
	}
}
