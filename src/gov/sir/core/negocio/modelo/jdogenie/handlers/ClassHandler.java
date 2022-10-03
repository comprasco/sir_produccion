package gov.sir.core.negocio.modelo.jdogenie.handlers;

import gov.sir.core.negocio.modelo.jdogenie.LlaveMapaJDO;
import gov.sir.core.negocio.modelo.jdogenie.ValorMapaJDO;

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
public class ClassHandler extends DefaultHandler {
    
    private CharArrayWriter text = new CharArrayWriter();
    private Stack path;
    private Map params;
    private DefaultHandler parent;
    private SAXParser saxParser;
    private Map jdoMap;
    private String paquete;
    private String clase;
    private String tabla;    
    /**
     * @param handler 
     * @param saxParser 
     * @param attributes 
     * @param params 
     * @param path 
     * @param paquete 
     * @param jdoMap   
     * @throws SAXException */
    public ClassHandler(Map jdoMap, String paquete, Stack path, Map params, Attributes attributes, SAXParser saxParser, PackageHandler parent) throws SAXException {
        this.jdoMap=jdoMap;
        this.paquete=paquete;
        this.params=params;
        this.parent=parent;
        this.path=path;
        this.saxParser=saxParser;
        start(attributes);
    }
    
    public void start(Attributes attributes) throws SAXException {
        String name = attributes.getValue("name");
        this.clase=name;
    }
    
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        
            
        if (qName.equals("extension")){
            ValorMapaJDO v=new ValorMapaJDO();
            path.push("extension");
            DefaultHandler handler = new ExtensionHandler(jdoMap, v, path,
                    params, attributes, saxParser, this);
            saxParser.getXMLReader().setContentHandler(handler);
            if (v.getTabla()!=null){
                tabla=v.getTabla();    
            }   
        }
        if (qName.equals("field")){
            LlaveMapaJDO llave = new LlaveMapaJDO();
            ValorMapaJDO valor=new ValorMapaJDO();
            
            llave.setClase(paquete+"."+clase);
            valor.setTabla(tabla);
            path.push("field");
            DefaultHandler handler = new FieldHandler(jdoMap,llave, valor, path,
                    params, attributes, saxParser, this);
            saxParser.getXMLReader().setContentHandler(handler);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("class")){
            path.pop();
            saxParser.getXMLReader().setContentHandler(parent);
        }
    }

}
