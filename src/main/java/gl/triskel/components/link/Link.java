package gl.triskel.components.link;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.annotations.Page;
import gl.triskel.components.Component;
import gl.triskel.components.WebPage;

public class Link extends Component{

	private Class<? extends WebPage> page;
	private String text;
	
	private Set<LinkParameter> parameters;
	
	public Link() 
	{}
	
	
	public Link(Class<? extends WebPage> page, String text, LinkParameter... parameters)
	{
		this.page = page;
		this.text = text;
		this.parameters = new HashSet<LinkParameter>();
		this.parameters.addAll(Arrays.asList(parameters));
	}
	
	public void setPage(Class<? extends WebPage> page) {
		this.page = page;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void addParameter(LinkParameter parameter)
	{
		if (parameters == null) parameters = new HashSet<LinkParameter>();
		parameters.add(parameter);
	}
	

	@Override
	public Element render(Document doc) {
		
		//Create the root element.
		Element aElement = doc.createElement("a");
		

		if (page != null)
		{
			String href = ((Page)page.getAnnotation(Page.class)).relativePath();
			if (!parameters.isEmpty())
			{
				href += "?";
				Iterator<LinkParameter> iter = parameters.iterator();
				while (iter.hasNext())
				{
					LinkParameter parameter = iter.next();
					href += parameter.getName() + "=" + parameter.getValue().toString();
					//if(iter.hasNext()) href += "&amp;";
					if(iter.hasNext()) href += "&";
				}
					
			}
			aElement.setAttribute("href", href);
		}
		if(this.getText() != null) 
			aElement.setTextContent(this.getText());

		return aElement;
	}
	
}
