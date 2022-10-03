package gov.sir.core.negocio.modelo.jdogenie.handlers;

import gov.sir.forseti.ForsetiProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author jfrias
 *
 */
public class JDOHandler extends DefaultHandler {
    
    private SAXParser saxParser;
    private Stack path = new Stack();
    private Map params = new HashMap();
    private Map jdoMap = new HashMap();
    private static JDOHandler instance=new JDOHandler();
    
    /**  */
    private JDOHandler() {
        try {
            init();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static JDOHandler getInstance(){
        return instance;
    }

    /**
     * @param fis
     */
    private void init() throws SAXException, IOException {
        
        String llave=ForsetiProperties.MAPA_JDO;
        String archivo=ForsetiProperties.getInstancia().getProperty(llave);
        
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream is=classLoader.getResourceAsStream(archivo);
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        saxParser=null;

        try {
            saxParser = factory.newSAXParser();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        if (saxParser!=null){
            saxParser.getXMLReader().setContentHandler(this);
            saxParser.getXMLReader().parse(new InputSource(is));
        }
        
        /*int tam=jdoMap.size();
        
        String clase="gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced";
        String atributo="tipoPredio";
        
        LlaveMapaJDO llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        int cde=llave.hashCode();
        
        ValorMapaJDO valor=(ValorMapaJDO)jdoMap.get(llave);

        clase="gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced";
        atributo="idMatricula";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        
        clase="gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced";
        atributo="anotaciones";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        //No deberia
        valor=(ValorMapaJDO)jdoMap.get(llave);
        
        //---------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced";
        atributo="idZonaRegistral";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced";
        atributo="idAnotacion";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced";
        atributo="idAnotacion1";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced";
        atributo="idOficio";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced";
        atributo="fechaCreacion";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced";
        atributo="numeroTimbre";
        
        Class c=JDOHandler.class.getSuperclass();
        String asdfds=c.getName();
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.PlantillaPertenenciaEnhanced";
        atributo="descripcion";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced";
        atributo="tipoRecepcion";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced";
        atributo="datosAntiguoSistema";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced";
        atributo="nombre";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.SubAtencionSubSolicitudEnhanced";
        atributo="subtipoAtencion";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced";
        atributo="idTipoOficina";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        clase="gov.sir.hermod.dao.impl.jdogenie.Procesos_V";
        atributo="tipo_fase";
        
        llave=new LlaveMapaJDO();
        llave.setClase(clase);
        llave.setAtributo(atributo);
        
        cde=llave.hashCode();
        
        valor=(ValorMapaJDO)jdoMap.get(llave);
        //-------------------------
        
        return jdoMap;
        //return params;
        */ 
    }

    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
        if (qName.equals("package")) {
            path.push("package");
            DefaultHandler handler = new PackageHandler(jdoMap, path,
                    params, attributes, saxParser, this);
            saxParser.getXMLReader().setContentHandler(handler);
        }
    }
    
    public Map getJdoMap() {
        return jdoMap;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JDOHandler parserInicial = new JDOHandler();
        Map mapa=null;
        FileInputStream fis;
        try {
            parserInicial.init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
