package gov.sir.core.web.acciones.comun;

import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.jdogenie.ObsoletoEnhanced;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.print.server.PrintJobsException;
import gov.sir.print.server.dao.DAOException;
import gov.sir.print.server.dao.FactoryException;
import gov.sir.print.server.dao.PrintFactory;

import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

public class AWPrefabricado extends SoporteAccionWeb {

	private static Logger log = Logger.getLogger(gov.sir.core.web.acciones.comun.AWPrefabricado.class);
	
	public static final String PF_CIRCULO = "CIRCULO";
	public static final String PF_CANTIDAD = "CANTIDAD";
	
	private ServiceLocator service = null;
	
	private PrintFactory impresion; 
	
	private ForsetiServiceInterface forseti;
	
	public AWPrefabricado() throws EventoException, PrintJobsException {
		service = ServiceLocator.getInstancia();
		
		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			
			try {
				impresion = PrintFactory.getFactory();
			}catch (FactoryException e) {
				throw new PrintJobsException("No fue posible obtener la fábrica concreta", e);
	
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio.", e);
		}
	}
	
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String circulo = request.getParameter(PF_CIRCULO) != null? request.getParameter(PF_CIRCULO) : "000";
		int cantidad = request.getParameter(PF_CANTIDAD) != null? Integer.parseInt(request.getParameter(PF_CANTIDAD)) : 0;
		
		new Thread(new PrefabricadoID(circulo, cantidad)).start() ;
		
		// TODO Auto-generated method stub
		return null;
	}
	
	   public class PrefabricadoID implements Runnable {
	    	private String circulo;
	    	private int cantidad;
	    	
	    	private PersistenceManager pm;
	    	
	    	
	    	public PrefabricadoID(String circulo, int cantidad){
	    		this.circulo = circulo;
	    		this.cantidad = cantidad;	    		
	    	}
	    	
	    	private ImprimibleCertificado getFolioByID(String matricula) throws Throwable{
	    		Folio folio=null;		
				folio = forseti.getFolioByMatricula( matricula );

	    		List anotacionesFolio = folio.getAnotaciones();
	    		
	    		if (anotacionesFolio == null || anotacionesFolio.isEmpty())
	    		{
	    			FolioPk fpk = new FolioPk();
	    			fpk.idMatricula = matricula;
	    			anotacionesFolio = forseti.getAnotacionesFolio(fpk);
	    			folio.setAnotaciones(anotacionesFolio);
	    		}
	    		
	    		FolioPk fid = new FolioPk();
	    		fid.idMatricula = folio.getIdMatricula();

	    		// obtenemos los folios padres
	    		List padres=forseti.getFoliosPadre(fid);
	    		// obtenemos los folios hijos
	    		List hijos=forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                        /**
                        * @author: David Panesso
                        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                        * Nuevo listado de folios derivados
                        **/
                        List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);
	    		
	    		return new ImprimibleCertificado(folio, null, padres, hijos, foliosDerivadoHijos, null, null, null, null);	
	    	}
	    	
			public void run() {
				ImprimibleCertificado imprimible = null;
				List obsoletos = null;
				ObsoletoEnhanced obsoleto;
				
				try {
					obsoletos = impresion.getImpresionDAO().getObsoletos(this.circulo, this.cantidad);
					
					if(obsoletos != null && obsoletos.size()>0){
						try{						
				            for (Iterator itr = obsoletos.iterator(); itr.hasNext();) {
				                obsoleto = (ObsoletoEnhanced) itr.next();
				                
								imprimible = this.getFolioByID(obsoleto.getIdMatricula());
								impresion.getImpresionDAO().guardarPrefabricado(imprimible);
								log.debug("Actualizado el obsoleto con matricula " + obsoleto.getIdMatricula());
							}
						} catch(Exception e){	
							throw new DAOException(e.getMessage(), e);
						} catch (Throwable e) {
							throw new DAOException(e.getMessage(), e);
						}
					}
				} catch (DAOException e) {
					log.error(e.getMessage(), e);
				}
				
				
			}
		}
		
}
