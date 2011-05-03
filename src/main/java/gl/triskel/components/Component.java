package gl.triskel.components;

import gl.triskel.components.interfaces.Renderizable;

public abstract class Component implements Renderizable{

	private String id;
	private Component parent;

	public Component()
	{}
	
	public Component(String id)
	{
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setParent(Component parent) {
		this.parent = parent;
	}

	public Component getParent() {
		return parent;
	}
	
	
	
}
