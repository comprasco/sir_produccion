package gov.sir.core.negocio.modelo.jdogenie.handlers;

import gov.sir.core.negocio.modelo.jdogenie.LlaveMapaJDO;
import gov.sir.core.negocio.modelo.jdogenie.RelAtributoValorMapaJDO;
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
public class ExtensionHandler extends DefaultHandler {
    private CharArrayWriter text = new CharArrayWriter();
    private Stack path;
    private Map params;
    private DefaultHandler parent;
    private SAXParser saxParser;
    private Map jdoMap;
    private LlaveMapaJDO llave;
    private ValorMapaJDO valor;
    private RelAtributoValorMapaJDO relacion;
        
    /**
     *  @param jdoMap
    /** @param valor
    /** @param path
    /** @param params
    /** @param attributes
    /** @param saxParser
    /** @param parent
    /** @throws SAXException
     */
    public ExtensionHandler(Map jdoMap, ValorMapaJDO valor, Stack path, Map params, Attributes attributes, SAXParser saxParser, ClassHandler parent) throws SAXException {
        this.jdoMap=jdoMap;
        this.valor=valor;
        this.params=params;
        this.parent=parent;
        this.path=path;
        this.saxParser=saxParser;
        start(attributes);
    }
    
    /**
     *  @param jdoMap
    /** @param valor
    /** @param path
    /** @param params
    /** @param attributes
    /** @param saxParser
    /** @param parent
    /** @throws SAXException
     * @param llave 
     */
    public ExtensionHandler(Map jdoMap, LlaveMapaJDO llave, ValorMapaJDO valor, Stack path, Map params, Attributes attributes, SAXParser saxParser, FieldHandler parent) throws SAXException {
        this.jdoMap=jdoMap;
        this.valor=valor;
        this.llave=llave;
        this.params=params;
        this.parent=parent;
        this.path=path;
        this.saxParser=saxParser;
        start(attributes);
    }
    
    /**
     *  @param jdoMap
    /** @param valor
    /** @param path
    /** @param params
    /** @param attributes
    /** @param saxParser
    /** @param parent
    /** @throws SAXException
     * @param llave 
     */
    public ExtensionHandler(Map jdoMap, LlaveMapaJDO llave, ValorMapaJDO valor, Stack path, Map params, Attributes attributes, SAXParser saxParser, ExtensionHandler parent) throws SAXException {
        this.jdoMap=jdoMap;
        this.valor=valor;
        this.llave=llave;
        this.params=params;
        this.parent=parent;
        this.path=path;
        this.saxParser=saxParser;
        start(attributes);
    }
    
    /**
     *  @param jdoMap
    /** @param llave
    /** @param valor
    /** @param relacion
    /** @param path
    /** @param params
    /** @param attributes
    /** @param saxParser
    /** @param parent
    /** @throws SAXException
     */
    public ExtensionHandler(Map jdoMap, LlaveMapaJDO llave, ValorMapaJDO valor, RelAtributoValorMapaJDO relacion, Stack path, Map params, Attributes attributes, SAXParser saxParser, ExtensionHandler parent) throws SAXException {
        this.jdoMap=jdoMap;
        this.valor=valor;
        this.llave=llave;
        this.relacion=relacion;
        this.params=params;
        this.parent=parent;
        this.path=path;
        this.saxParser=saxParser;
        start(attributes);
    }
    
    public void start(Attributes attributes) throws SAXException {
        String key= attributes.getValue("key");
        String value= attributes.getValue("value");
        
        if (key.equals("jdbc-table-name")){
            valor.setTabla(value);
        }else if (key.equals("jdbc-column-name")){
            if (relacion==null){
                relacion=new RelAtributoValorMapaJDO();
            }
            relacion.setColumna(value);
            valor.addColumna(relacion);
            jdoMap.put(llave, valor);
        }else if (key.equals("jdbc-ref")){
            relacion = new RelAtributoValorMapaJDO();
            relacion.setAtributo(value);
        }
    }
    
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        
        if (qName.equals("extension")){
            if (relacion==null){
                path.push("extension");
                DefaultHandler handler = new ExtensionHandler(jdoMap, llave, valor, path,
                        params, attributes, saxParser, this);
                saxParser.getXMLReader().setContentHandler(handler);
            }else{
                path.push("extension");
                DefaultHandler handler = new ExtensionHandler(jdoMap, llave, valor, relacion, path,
                        params, attributes, saxParser, this);
                saxParser.getXMLReader().setContentHandler(handler);
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("extension")){
            path.pop();
            saxParser.getXMLReader().setContentHandler(parent);
        }
    }

}
