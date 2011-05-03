package gl.triskel.core.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Document;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class HtmlFormatter {
    public static String format(Document doc) {
        try {
            
            OutputFormat format = new OutputFormat(doc);
            
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            format.setOmitXMLDeclaration(true);

            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(doc);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }	
}
