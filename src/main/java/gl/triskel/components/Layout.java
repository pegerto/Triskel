package gl.triskel.components;

import gl.triskel.components.interfaces.Composite;

import java.util.ArrayList;
import java.util.List;

public abstract class Layout extends Component implements Composite{

	private List<Component> components;
	
	public void addComponet(Component component)
	{
		if (components == null)
		{
			components = new ArrayList<Component>();
		}
		component.setParent(((Component)this));
		components.add(component);
	}
	
	public List<Component> getComponets()
	{
		return components;
	}
	public boolean hasChildrens()
	{
		return components != null; 
	}
	
	//alias to addComponent
	public void add(Component component)
	{
		this.addComponet(component);
	}
}
