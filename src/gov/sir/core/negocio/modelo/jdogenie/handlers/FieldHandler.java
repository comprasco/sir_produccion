package gov.sir.core.negocio.modelo.jdogenie.handlers;

import gov.sir.core.negocio.modelo.jdogenie.LlaveMapaJDO;
import gov.sir.core.negocio.modelo.jdogenie.ValorMapaJDO;

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
public class FieldHandler extends DefaultHandler {
    private Stack path;
    private Map params;
    private DefaultHandler parent;
    private SAXParser saxParser;
    private Map jdoMap;
    private LlaveMapaJDO llave;
    private ValorMapaJDO valor;
    /**
     * @param handler 
     * @param saxParser 
     * @param attributes 
     * @param params 
     * @param path 
     * @param llave 
     * @param jdoMap   
     * @param valor 
     * @throws SAXException */
    public FieldHandler(Map jdoMap, LlaveMapaJDO llave, ValorMapaJDO valor, Stack path, Map params, Attributes attributes, SAXParser saxParser, ClassHandler parent) throws SAXException {
        this.jdoMap=jdoMap;
        this.llave=llave;
        this.valor=valor;
        this.params=params;
        this.parent=parent;
        this.path=path;
        this.saxParser=saxParser;
        start(attributes);
    }
    
    public void start(Attributes attributes) throws SAXException {
        String name= attributes.getValue("name");
        String pk= attributes.getValue("primary-key");
        llave.setAtributo(name);
        valor.setPk(pk!=null);
    }
    
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        
        if (qName.equals("extension")){
            path.push("extension");
            DefaultHandler handler = new ExtensionHandler(jdoMap, llave, valor, path,
                    params, attributes, saxParser, this);
            saxParser.getXMLReader().setContentHandler(handler);
            
        }
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("field")){
            path.pop();
            saxParser.getXMLReader().setContentHandler(parent);
        }
    }

}
