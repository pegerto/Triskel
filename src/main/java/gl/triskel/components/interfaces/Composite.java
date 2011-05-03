package gl.triskel.components.interfaces;

import java.util.List;

import gl.triskel.components.Component;

public interface Composite {
	public void addComponet(Component component);
	public List<Component> getComponets();
	public boolean hasChildrens();
}
