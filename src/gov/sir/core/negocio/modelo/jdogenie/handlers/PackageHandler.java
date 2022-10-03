package gov.sir.core.negocio.modelo.jdogenie.handlers;

import java.io.CharArrayWriter;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author jfrias
 *
 */
public class PackageHandler extends DefaultHandler {

    private CharArrayWriter text = new CharArrayWriter();
    private Stack path;
    private Map params;
    private DefaultHandler parent;
    private SAXParser saxParser;
    private Map jdoMap;
    private String paquete;
    /**
     * @param handler 
     * @param saxParser 
     * @param attributes 
     * @param params 
     * @param path 
     * @param jdoMap   
     * @throws SAXException */
    public PackageHandler(Map jdoMap, Stack path, Map params, Attributes attributes, SAXParser saxParser, JDOHandler parent) throws SAXException {
        this.jdoMap=jdoMap;
        this.params=params;
        this.parent=parent;
        this.path=path;
        this.saxParser=saxParser;
        start(attributes);
    }
    
    public void start(Attributes attributes) throws SAXException {
        String name = attributes.getValue("name");
        this.paquete=name;
    }

    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        if (qName.equals("class")){
            path.push("class");
            DefaultHandler handler = new ClassHandler(jdoMap, paquete, path,
                    params, attributes, saxParser, this);
            saxParser.getXMLReader().setContentHandler(handler);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("package")){
            path.pop();
            saxParser.getXMLReader().setContentHandler(parent);
        }
    }
    
    
    
    
    
}
