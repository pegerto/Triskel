/**
 * 
 */
package gl.triskel.components.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.components.Component;
import gl.triskel.components.Label;
import gl.triskel.components.interfaces.WebPageVisitor;
import gl.triskel.core.util.Pair;

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
public class Grid extends Component{

	private int rows;
	private int cols;
	private List<GridHeader> header;
	private Hashtable<Pair<Integer, Integer>, Component> data;
	
	public Grid(){
		data = new Hashtable<Pair<Integer,Integer>, Component>();
	}
	
	public Grid(int cols, int rows)
	{
		data = new Hashtable<Pair<Integer,Integer>, Component>();
		this.cols = cols;
		this.rows = rows;
	}
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	
	public void set(int row, int col, String text)
	{
		Label lab = new Label();
		lab.setText(text);
		set(row,col,lab);
	}
	
	public void set(int row, int col, Component component)
	{
		if (row < rows && col < cols) 
		{
			Pair<Integer, Integer> pos = new Pair<Integer, Integer>(row,col);
			data.put(pos, component);
		}else{
			//TODO Throws component exception
		}
	}
	

	/* (non-Javadoc)
	 * @see gl.triskel.components.interfaces.Renderizable#render(org.w3c.dom.Document)
	 */
	@Override
	public Element render(Document doc) {
		
		Element grid = doc.createElement("table");
		
		//Grid header
		
		//Grid content
		for (int i = 0; i < rows; i++)
		{
			Element rowElement = doc.createElement("tr");
			for (int j = 0; j < cols; j++)
			{
				Element colElement = doc.createElement("td");
				
				Component comp = data.get(new Pair<Integer, Integer>(i, j));
				if (comp != null) colElement.appendChild(comp.render(doc));
				
				rowElement.appendChild(colElement);
			}
			
			grid.appendChild(rowElement);
		}
		return grid;
	}

	/* (non-Javadoc)
	 * @see gl.triskel.components.Component#accept(gl.triskel.components.interfaces.WebPageVisitor)
	 */
	@Override
	public void accept(WebPageVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
