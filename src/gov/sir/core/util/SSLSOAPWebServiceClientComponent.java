package gov.sir.core.util;



import gov.sir.core.util.tls.TLSSocketConnectionFactory;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
   

/**
 * Clase Componente que Encapsula Logica de 
 * Consumo a través de SSL de WebServices Seguros de VUR 
 * @author DNilson Olaya Gómez - desarrollo3@tsg.net.co
 */
public class SSLSOAPWebServiceClientComponent {
    private static final Logger logger = Logger.getLogger(SSLSOAPWebServiceClientComponent.class.getName());
    private static final String TURNO_ASIGNADO = "002";
    private String turnoAsignado;
        /**
         * 
         * Metodo main para probar unitariamente 
         * el Consumo de los WebServices de VUR de Instrumentos Publicos
         * @param args
         * @throws Exception 
         * 
         * @author DNilson Olaya Gómez - desarrollo3@tsg.net.co
         * 
         */
        
	public static void main(String[] args) throws Exception {
		                
                String END_POINT = "https://www.abcpagos.com/InstrumentosPublicos.svc?wsdl";
		
		logger.info(String.format("<<<<> DNilson226 Inicio Prueba Unitaria Snippet : %s <>>>>", END_POINT));
                System.out.println("<<<<> DNilson226 END_POINT : "+END_POINT+" <>>>>");    
                
                SSLSOAPWebServiceClientComponent instanciaClase = new SSLSOAPWebServiceClientComponent();
                
                instanciaClase.testServicioConsultaPIN();
                
                System.out.println("\n\n<<<<>>>*******DNilson226 *******<<<>>>>>\n\n");
                
                instanciaClase.testServicioAsignaTurno();    
                
	}
        
    /**
     * método de prueba Unitaria del ServicioVUR ConsultaPIN
     * @throws Exception 
     * @author DNilson Olaya Gómez
     */    
    public void testServicioConsultaPIN() throws Exception{
        
        
                String sparamToken = "$SaUYuP5nhc52UGsa5uIIxOUV4YcvttFHwp9TQM2o";
                String sparamPIN ="11100123049376";             
                
                System.out.println("<<<<>>>*******DNilson226 Into testServicioConsultaPIN() *******<<<>>>>>\n");
                logger.info("<<<<>>>*******DNilson226 Into testServicioConsultaPIN() *******<<<>>>>>\n");                
                
                //byte[] respuesta = callSOAPWebServiceConsultaPIN(sparamToken, sparamPIN);
                
                String codRespuestaSrvConsultaPIN = callSOAPWebServiceConsultaPIN(sparamToken, sparamPIN);
                
                logger.info("<<<<> DNilson226 codRespuestaSrvConsultaPIN.toString() : "+codRespuestaSrvConsultaPIN+" \n <>>>>");
                
                System.out.println("<<<<> DNilson226 codRespuestaSrvConsultaPIN.toString() : \n "+
                                                codRespuestaSrvConsultaPIN+" \n <>>>>");                
                System.out.println("<<<<> DNilson226 <>>>> \n\n");   
    }    
    
    /**
     * método de prueba Unitaria del ServicioVUR AsignaTurno
     * @throws Exception 
     * @author DNilson Olaya Gómez     
     */
    public void testServicioAsignaTurno() throws Exception{
        
                String sparamToken = "$SaUYuP5nhc52UGsa5uIIxOUV4YcvttFHwp9TQM2o";
                String sparamPIN = "11100123049376";
                String sparamTurno = "2021-999-6-1";
                String sparamOrigen = "1";                          
                
                System.out.println("<<<<>>>*******DNilson226 Into testServicioAsignaTurno() *******<<<>>>>>\n");
                logger.info("<<<<>>>*******DNilson226 Into testServicioAsignaTurno() *******<<<>>>>>\n");                
                
                String codRespuestaSrvAsignaTurno = callSOAPWebServiceAsignaTurno(sparamToken, sparamPIN,
                                                    sparamTurno, sparamOrigen);
                
                logger.info("<<<<> DNilson226 codRespuestaSrvAsignaTurno.toString() : "+codRespuestaSrvAsignaTurno+" \n <>>>>");
                
                System.out.println("<<<<> DNilson226 codRespuestaSrvAsignaTurno.toString() : \n "+
                                                codRespuestaSrvAsignaTurno+" \n <>>>>");                
                System.out.println("<<<<> DNilson226 <>>>> \n\n");                     
        
    }
    
    /**
     * 
     * @param xmlRespVUR
     * @return codigo de Respuesta Servicio VUR
     * @author DNilson226 - Nilson Olaya Gómez 
     */
    private static String procesarXMLRespuestaVURWebServices(String xmlRespVUR){
        
        String codigoRespuestaSrvVUR = null;
        
        Document documentoXML = convertStringToXMLDocument( xmlRespVUR );
        
        //Verify XML document is build correctly
        System.out.println(documentoXML.getFirstChild().getNodeName());
        
                
        NodeList nList = documentoXML.getElementsByTagName("a:cod_respuesta");
       
        if(nList != null && nList.getLength()>0){
            codigoRespuestaSrvVUR = nList.item(0).getTextContent();
            System.out.println("<>>>>>DNilson226 vlr etiqueta codRespuesta: "+codigoRespuestaSrvVUR+"<>>>>>\n\n");   
        }     
        
        return codigoRespuestaSrvVUR;        
    }
    
    private void procesarTurnoXMLRespuestaVURWebServices(String xmlRespVUR){
        
        String codigoRespuestaSrvVUR = null;
        
        Document documentoXML = convertStringToXMLDocument( xmlRespVUR );
        
        //Verify XML document is build correctly
        System.out.println(documentoXML.getFirstChild().getNodeName());
        
                
        NodeList nList = documentoXML.getElementsByTagName("a:turno");
       
        if(nList != null && nList.getLength()>0){
            codigoRespuestaSrvVUR = nList.item(0).getTextContent();
            System.out.println("<>>>>>DNilson226 vlr etiqueta codRespuesta: "+codigoRespuestaSrvVUR+"<>>>>>\n\n");   
        }     
        
        if(codigoRespuestaSrvVUR != null){
            setTurnoAsignado(codigoRespuestaSrvVUR);
        }       
    }
    
    /**
     * 
     * @param xmlString
     * @return 
     */
    private static Document convertStringToXMLDocument(String xmlString) 
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }

        
    /**
     * 
     * @return
     * @throws Exception 
     */    
    private static HttpClient getHttpClient() throws Exception {
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                            new TLSSocketConnectionFactory(), new String[] { "TLSv1.2" }, null, new HostnameVerifier() {
                                    public boolean verify(String hostname, SSLSession session) {
                                            return true;
                                    }
                            });

            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }
    
        
    /**
     * 
     * @param paramToken
     * @param paramPIN
     * @return
     * @throws Exception 
     * 
     * Author: DNilson Olaya Gómez - desarrollo3@tsg.net.co
     *    
     */     
    public String callSOAPWebServiceConsultaPIN(String paramToken, 
                                                      String paramPIN) throws Exception {
        
        byte[] result = null;    
        String codigoRespuestaWSVUR = null;
        
        HttpClient httpclient = getHttpClient();

        String SERVER_URL = new String("https://www.abcpagos.com/InstrumentosPublicos.svc");
        String SOAP_ACTION = "http://tempuri.org/IInstrumentosPublicos/ConsultaPIN";
        
        /*
         * httpclient.getCredentialsProvider().setCredentials( new
         * AuthScope("os.icloud.com", 80, null, "Digest"), new
         * UsernamePasswordCredentials(username, password));
         */
        HttpPost httppost = new HttpPost(SERVER_URL );

        httppost.setHeader("soapaction", SOAP_ACTION);
        httppost.setHeader("Content-Type", "text/xml; charset=utf-8");
        String body = "";

        body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:ConsultaPIN>\n" +
                "         <!--Optional:-->\n" +
                "         <!-- tem:token>?</tem:token --> \n" +
                "         <tem:token>"+paramToken+"</tem:token>\n" +
                "         <!--Optional:-->\n" +
                "		<tem:PIN>"+paramPIN+"</tem:PIN>         \n" +
                "      </tem:ConsultaPIN>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        System.out.println("executing request" + httppost.getRequestLine());
       //now create a soap request message as follows:
        final StringBuffer soap = new StringBuffer();
        soap.append("\n");
        soap.append("");
       // this is a sample data..you have create your own required data  BEGIN       
        soap.append(" \n");
        soap.append(" \n");
        soap.append("" + body);
        soap.append(" \n");
        soap.append(" \n");

        /* soap.append(body); */
         // END of MEssage Body
        soap.append("");
        //Log.i("SOAP Request", ""+soap.toString());
        System.out.println("<<<<>DNilson226 SOAP Request: "+soap.toString());
       // END of full SOAP request  message
        try {
            HttpEntity entity = new StringEntity(soap.toString(),HTTP.UTF_8);
            httppost.setEntity(entity); 
            HttpResponse response = httpclient.execute(httppost);// calling server
            HttpEntity r_entity = response.getEntity();  //get response
            //Log.i("Reponse Header", "Begin...");          // response headers
            System.out.println("<<<<>DNilson226 Response Header Begin...");          // response headers
            //Log.i("Reponse Header", "StatusLine:"+response.getStatusLine());
            System.out.println("<<<<>DNilson226 Response Header StatusLine:"+response.getStatusLine());
            Header[] headers = response.getAllHeaders();
            for(Header h:headers){
                //Log.i("Reponse Header",h.getName() + ": " + h.getValue());
                System.out.println("<<<<>DNilson226 Response Header"+h.getName() + ": " + h.getValue());
            }
            //Log.i("Reponse Header", "END...");
            System.out.println("<<<<>DNilson226 Response Header END...");
            if (r_entity != null) {       
                result = new byte[(int) r_entity.getContentLength()];  
                if (r_entity.isStreaming()) {
                    DataInputStream is = new DataInputStream(
                            r_entity.getContent());
                    is.readFully(result);
                }
            }
        } catch (Exception E) {
            System.err.println("<<<<>DNilson226 Exception While Connecting: "+E.getMessage());
            E.printStackTrace();
        }

        httpclient.getConnectionManager().shutdown(); //shut down the connection
        
        String strRespServicioConsultaPIN_VURInstrumentosPublicos = new String(result);
        
        codigoRespuestaWSVUR = procesarXMLRespuestaVURWebServices(strRespServicioConsultaPIN_VURInstrumentosPublicos);
        if(codigoRespuestaWSVUR != null && codigoRespuestaWSVUR.equals(TURNO_ASIGNADO)){
            procesarTurnoXMLRespuestaVURWebServices(strRespServicioConsultaPIN_VURInstrumentosPublicos);
        }
            
    return codigoRespuestaWSVUR;
   }
    
   /**
    * 
    * @param paramToken
    * @param paramPIN
    * @param paramTurno
    * @param origen
    * @return
    * @throws Exception 
    * Author: DNilson Olaya Gómez - desarrollo3@tsg.net.co
    * 
    */ 
   public String callSOAPWebServiceAsignaTurno(String paramToken, String paramPIN,
                                         String paramTurno, String paramOrigen) throws Exception {
        byte[] result = null;    
        String codigoRespuestaWSVUR = null;
        
        HttpClient httpclient = getHttpClient();

        String SERVER_URL = new String("https://www.abcpagos.com/InstrumentosPublicos.svc");
        // String SOAP_ACTION = "http://tempuri.org/IInstrumentosPublicos/ConsultaPIN";
        String SOAP_ACTION = "http://tempuri.org/IInstrumentosPublicos/AsignaTurno";
        
        /*
         * httpclient.getCredentialsProvider().setCredentials( new
         * AuthScope("os.icloud.com", 80, null, "Digest"), new
         * UsernamePasswordCredentials(username, password));
         */
        HttpPost httppost = new HttpPost(SERVER_URL );

        httppost.setHeader("soapaction", SOAP_ACTION);
        httppost.setHeader("Content-Type", "text/xml; charset=utf-8");
        String body = "";

        body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:AsignaTurno>\n" +
                "         <!--Optional:-->\n" +
                "         <tem:token>"+paramToken+"</tem:token>\n" +
                "         <!--Optional:-->\n" +
                "         <tem:PIN>"+paramPIN+"</tem:PIN>\n" +
                "         <!--Optional:-->\n" +
                "         <tem:turno>"+paramTurno+"</tem:turno>\n" +
                "         <!--Optional:-->\n" +
                "         <tem:origen>"+paramOrigen+"</tem:origen>\n" +
                "      </tem:AsignaTurno>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        System.out.println("executing request" + httppost.getRequestLine());
       //now create a soap request message as follows:
        final StringBuffer soap = new StringBuffer();
        soap.append("\n");
        soap.append("");
       // this is a sample data..you have create your own required data  BEGIN       
        soap.append(" \n");
        soap.append(" \n");
        soap.append("" + body);
        soap.append(" \n");
        soap.append(" \n");

        /* soap.append(body); */
         // END of MEssage Body
        soap.append("");
        //Log.i("SOAP Request", ""+soap.toString());
        System.out.println("<<<<>DNilson226 SOAP Request: "+soap.toString());
       // END of full SOAP request  message
        try {
            HttpEntity entity = new StringEntity(soap.toString(),HTTP.UTF_8);
            httppost.setEntity(entity); 
            HttpResponse response = httpclient.execute(httppost);// calling server
            HttpEntity r_entity = response.getEntity();  //get response
            //Log.i("Reponse Header", "Begin...");          // response headers
            System.out.println("<<<<>DNilson226 Reponse Header Begin...");          // response headers
            //Log.i("Reponse Header", "StatusLine:"+response.getStatusLine());
            System.out.println("<<<<>DNilson226 Reponse Header StatusLine:"+response.getStatusLine());
            Header[] headers = response.getAllHeaders();
            for(Header h:headers){
                //Log.i("Reponse Header",h.getName() + ": " + h.getValue());
                System.out.println("<<<<>DNilson226 Reponse Header"+h.getName() + ": " + h.getValue());
            }
            //Log.i("Reponse Header", "END...");
            System.out.println("<<<<>DNilson226 Reponse Header END...");
            if (r_entity != null) {       
                result = new byte[(int) r_entity.getContentLength()];  
                if (r_entity.isStreaming()) {
                    DataInputStream is = new DataInputStream(
                            r_entity.getContent());
                    is.readFully(result);
                }
            }
        } catch (Exception E) {
            System.err.println("<<<<>DNilson226 Exception While Connecting "+E.getMessage());
            E.printStackTrace();
        }
        
        httpclient.getConnectionManager().shutdown(); //shut down the connection
        
        String strRespServicioAsignaTurno_VURInstrumentosPublicos = new String(result);
        codigoRespuestaWSVUR = procesarXMLRespuestaVURWebServices(strRespServicioAsignaTurno_VURInstrumentosPublicos);
        
    return codigoRespuestaWSVUR;
   } 

    public String getTurnoAsignado() {
        return turnoAsignado;
    }

    public void setTurnoAsignado(String turnoAsignado) {
        this.turnoAsignado = turnoAsignado;
    }
        
   
   
        
}
