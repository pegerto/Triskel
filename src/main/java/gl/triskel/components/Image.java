package gl.triskel.components;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Image extends Component {

	private String resourcePath;
	
	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	@Override
	public Element render(Document doc) {
		Element imgElement = doc.createElement("img");
		if(resourcePath != null)
			imgElement.setAttribute("src", resourcePath);
		
		return imgElement;
	}

}
