package gl.triskel.components;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Label extends Component{

	private String text;
	
	public Label()
	{}
	
	public Label(String text) {
		this.text = text;
	}
	
	public Label(String id, String text)
	{
		this.setId(id);
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}

	@Override
	public Element render(Document doc) {
		
		Element spanElement = doc.createElement("span");

		//Element id
		if (this.getId() != null)
			spanElement.setAttribute("id", this.getId());
		
		//Text of the element
		if(this.getText() != null)
			spanElement.setTextContent(this.getText());
		
		
		return spanElement;
	}

	
	
}
