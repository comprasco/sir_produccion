package gov.sir.core.util;



import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.util.tls.TLSSocketConnectionFactory;
import gov.sir.core.web.WebKeys;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;


public class TLSHttpClientComponent {
    private static final Logger logger = Logger.getLogger(TLSHttpClientComponent.class.getName());
    private static final String ERROR_404 = "404";
    private String REL_END_POINT = "https://radicacion.supernotariado.gov.co/services/external/sir?";
    private String status;
    private String uri;
    private String fase;

    
        public void sendMsgToREL(String codigoIdWorkFlowTurnoSIR) throws Exception{
            try{
                REL_END_POINT = buildURL(codigoIdWorkFlowTurnoSIR);
                
                if(REL_END_POINT == null){
                    String[] circulo = codigoIdWorkFlowTurnoSIR.split("-");
                    logger.info(String.format(" REL URL : %s", REL_END_POINT));
                    System.out.println(" REL URL : "+REL_END_POINT);
                    REL_END_POINT =  REL_END_POINT + "OFICINA="+circulo[1]+"&TURNO="+codigoIdWorkFlowTurnoSIR;
                }
                
                logger.info(String.format("URL Turno Reportado REL: %s", REL_END_POINT));
                System.out.println("URL Turno Reportado REL : "+REL_END_POINT);
		
                // add request headers
		HttpGet httpGet = new HttpGet(REL_END_POINT);
                HttpClient httpClient = getHttpClient();
		
		HttpResponse resp = httpClient.execute(httpGet);

		BufferedReader in = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		
		logger.info(String.format("%s : %s", resp.getStatusLine().getStatusCode(), resp.getStatusLine().getReasonPhrase()));
                System.out.println("resp.getStatusLine().getStatusCode() : "+resp.getStatusLine().getStatusCode()+" <>>>>");
                System.out.println("resp.getStatusLine().getReasonPhrase() : "+resp.getStatusLine().getReasonPhrase()+" <>>>>");
		logger.info(response.toString());
                System.out.println("response.toString() : "+resp.getStatusLine().getStatusCode()+" <>>>>");       
                System.out.println("STATUS: "+resp.getStatusLine().getStatusCode()); 
                System.out.println("    WF: "+codigoIdWorkFlowTurnoSIR); 
                System.out.println("   URI: "+REL_END_POINT); 
                
                status=""+resp.getStatusLine().getStatusCode();
                uri=REL_END_POINT;    
                System.out.println("TURNO REL REPORTADO EXITOSAMENTE");
                HermodService hs = HermodService.getInstance();
                hs.setStatusREL(status, uri, codigoIdWorkFlowTurnoSIR);
            } catch(Exception e){
                System.out.println("ERROR: NO FUE POSIBLE REPORTAR EL TURNO DEBIDO A " + e);
                HermodService hs = HermodService.getInstance();
                hs.setStatusREL(ERROR_404, REL_END_POINT, codigoIdWorkFlowTurnoSIR);
            } 
        }
        
        public String buildURL(String turnoWF){
            String endpointREL = "";
            try{
                HermodService hs = HermodService.getInstance();
                Turno turno = hs.getTurnobyWF(turnoWF);
                String begin = hs.getValorLookupCodes(COPLookupTypes.REL_ENDPOINT, COPLookupCodes.REL_ENDPOINT);
                List parameters = hs.getOPLookupCodesByTipo(COPLookupTypes.PARAMETROS_REL);
                if(begin != null){
                    endpointREL += begin;
                    if(parameters != null && !parameters.isEmpty()){
                        Iterator itPam = parameters.iterator();
                        while(itPam.hasNext()){
                            OPLookupCodes parameter = (OPLookupCodes) itPam.next();
                            if(parameter.getDescripcion().equals(WebKeys.SWITCH_ON)){
                                if(parameter.getValor().equals(COPLookupCodes.OFICINA)){
                                    endpointREL += parameter.getValor() + "=" + turno.getIdCirculo() + "&";
                                }
                                if(parameter.getValor().equals(COPLookupCodes.TURNO)){
                                    endpointREL += parameter.getValor() + "=" + turno.getIdWorkflow() ;
                                }
                                if(parameter.getValor().equals(COPLookupCodes.TRNO_ID_FASE)){
                                    if(fase != null){
                                        endpointREL += "&" + parameter.getValor() + "=" + fase;
                                    } else{
                                      return null;  
                                    }
                                }
                            }
                        }
                    } else{
                        return null;
                    }
                } else {
                    return null;
                }
                
            } catch(HermodException he){
               System.out.println("ERROR: " + he.getMessage());
            }
            return endpointREL;
        }

        /**
         * 
         * Metodo main para probar unitariamente 
         * el llamado a Componente de REL
         * @param args
     * @return 
         * @throws Exception 
         * 
         * @author DNilson Olaya Gómez - desarrollo3@tsg.net.co
         * 
         */
        /*
	public static void main(String[] args) throws Exception {
		
                String END_POINT = "https://sandbox-radicacion.supernotariado.gov.co/services/external/sir?OFICINA=50C&TURNO=2019-34454";
		
		logger.info(String.format("<<<<> DNilson226 END_POINT : %s", END_POINT));
                System.out.println("<<<<> DNilson226 END_POINT : "+END_POINT+" <>>>>");
		
                // add request headers
		HttpGet httpGet = new HttpGet(END_POINT);              
       

		HttpClient httpClient = getHttpClient();
		
		HttpResponse resp = httpClient.execute(httpGet);

		BufferedReader in = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		
		logger.info(String.format("%s : %s", resp.getStatusLine().getStatusCode(), resp.getStatusLine().getReasonPhrase()));
                System.out.println("<<<<> DNilson226 resp.getStatusLine().getStatusCode() : "+resp.getStatusLine().getStatusCode()+" <>>>>");
                System.out.println("<<<<> DNilson226 resp.getStatusLine().getReasonPhrase() : "+resp.getStatusLine().getReasonPhrase()+" <>>>>");
		logger.info(response.toString());
                System.out.println("<<<<> DNilson226 response.toString() : "+resp.getStatusLine().getStatusCode()+" <>>>>");
	}
        */

	public static HttpClient getHttpClient() throws Exception {
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
				new TLSSocketConnectionFactory(), new String[] { "TLSv1.2" }, null, new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
		
		return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
	}
        
        
        /**
         * @return the status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status the status to set
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return the uri
         */
        public String getUri() {
            return uri;
        }

        /**
         * @param uri the uri to set
         */
        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getFase() {
            return fase;
        }

        public void setFase(String fase) {
            this.fase = fase;
        }
        
}
