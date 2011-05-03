package gl.triskel.components.form;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.components.Component;
import gl.triskel.components.Layout;

public class Form extends Layout{

	@Override
	public Element render(Document doc) {
		Element formElement = doc.createElement("form");
	
		//Only POST method is allowed.
		formElement.setAttribute("method", "POST");
	
		if (this.hasChildrens())
		{
			for(Component component: this.getComponets())
			{
				formElement.appendChild(component.render(doc));
			}
		}
		return formElement;
	}

}
