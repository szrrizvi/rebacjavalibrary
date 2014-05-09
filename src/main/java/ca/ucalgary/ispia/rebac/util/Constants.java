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

/**
 * @author Mona Hosseinkhani
 */

/**
 * Holds a number of constant values used for model checking
 */
public class Constants {
		// Variable names
		public static final String owner="own"; 
		public static final String requestor="req";
		public static final String var_name_start = "x";
		
		// Cache size
		public static final int cache_size = 8000000;
		//alpha must be greater than zero and less than or equal one.
		public static final float alpha = .5f; 

}
