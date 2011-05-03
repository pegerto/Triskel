package gl.triskel.components.form;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.components.Component;

public class SubmitButton extends Component{

	@Override
	public Element render(Document doc) {
		Element inputElement= doc.createElement("input");
		inputElement.setAttribute("type", "submit");
	
		return inputElement;
	}

	
}
