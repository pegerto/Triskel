/**
 * 
 */
package gl.triskel.core.util;

/**
 * Triskel Web Framework 
 * A Coruña 2011
 *   
 *  
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author pegerto
 *
 */
public class Pair<L,R> {
	
	  private L left; 
	  private R right; 

	public Pair(L left, R right) {
		super();
		this.left = left;
		this.right = right;
	}
	public L getLeft() { return left; } 
	  public R getRight() { return right; } 
	  
	  public int hashCode() { return left.hashCode() ^ right.hashCode(); } 
	
	  public boolean equals(Object o) { 
	    if (o == null) return false; 
	    if (!(o instanceof Pair)) return false; 
	    Pair pairo = (Pair) o; 
	    return this.left.equals(pairo.getLeft()) && 
	           this.right.equals(pairo.getRight()); 
	  } 
	} 

