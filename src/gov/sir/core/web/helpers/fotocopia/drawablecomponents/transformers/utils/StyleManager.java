/*
 * Created on 29/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.sir.core.web.helpers.fotocopia.drawablecomponents.transformers.utils;

import javax.servlet.jsp.JspWriter;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.dom.DOMSource;

import java.io.Reader;
import java.io.StringReader;

/**
 * @author andres
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StyleManager {

	public static
	void transformOutput ( JspWriter out, String xmlString, String xslFileName )
	throws Exception  {
		
		Reader reader = new StringReader( xmlString );
		javax.xml.transform.stream.StreamSource
		source = new javax.xml.transform.stream.StreamSource(reader); 		
	
		org.w3c.dom.Document xml = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new java.io.ByteArrayInputStream(xmlString.getBytes()));
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer= tFactory.newTransformer( new StreamSource(new java.io.File( xslFileName) ) );		
		transformer.transform( new DOMSource( xml ), new StreamResult( out ) );

	}
}
