package gl.triskel.components;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.annotations.CssStyle;
import gl.triskel.annotations.Page;

public class WebPage extends Layout {

	public Element render(Document doc) {
		//Invoke the render action of webpage.
		onRender();
		
		Element htmlElement = doc.createElement("html");
		
		//Set the page title.
		String pageTitle = ((Page)this.getClass().getAnnotation(Page.class)).title();
		
		//Html encoding and definition
		htmlElement.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
		htmlElement.setAttribute("xml:lang", "en");
		
		//Head
		Element headElement = doc.createElement("head");
		htmlElement.appendChild(headElement);
		
		//Title
		Element titleElement = doc.createElement("title");
		titleElement.setTextContent(pageTitle);
		headElement.appendChild(titleElement);
		
		//Styles
		CssStyle cssStyle = (CssStyle)(this.getClass().getAnnotation(CssStyle.class));
		if (cssStyle != null)
		{
			Element linkElement = doc.createElement("link");
			linkElement.setAttribute("type", "text/css");
			linkElement.setAttribute("rel", "stylesheet");
			linkElement.setAttribute("href", cssStyle.relativePath());
			headElement.appendChild(linkElement);
		}
			
		
		
		//Body
		Element bodyElement = doc.createElement("body");
		htmlElement.appendChild(bodyElement);

		//Content
		if (this.hasChildrens())
		{
			for(Component component: this.getComponets())
			{
				bodyElement.appendChild(component.render(doc));
			}
		}
		
		return htmlElement;
	}
	

	protected void onRender()
	{}
	
	public void onLoad()
	{}
	
}
