package gov.sir.core.negocio.acciones.comun;

import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Alerta;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.web.WebKeys;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.smart.web.ProcesarAlertaInterfaz;

/**
 * @author mmunoz
 */
public class ProcesarAlertasImpl  implements ProcesarAlertaInterfaz{
	
	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;
	
	/**
	 * Constructor de la clase que implementa la interfaz que se encarga de relalizar
	 * la logica para el analisis de alertas.
	 * Inicializa todos los servicios.
	 */
	public ProcesarAlertasImpl() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod",e);
		}
		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}
    }


	/**
	 * Método que se encarga de analizar los objetos de negocio para concluir si se tiene 
	 * que mostrar algún tipo de alerta en la aplicacion.
	 * @param request Objeto donde se pasan los argumentos que estan siendo enviados por el cliente.
	 * @return HttpServletRequest con la informacion de las alertas que debe ser mostrada, y los argumentos
	 * originales que el usuario envio a la aplicación.
	 * @throws Throwable Execpcion generica ya que puede ocurrir algun error en cualquier lugar de la 
	 * aplicación.
	 */
	public HttpServletRequest procesarAlerta (HttpServletRequest request) throws Throwable {
		HttpSession session = request.getSession();
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		List mensajesAlertas = new ArrayList();		
		
		if(estacion != null){
			List alertas = hermod.getAlertas(estacion.getEstacionId());
			Iterator itAlertas = alertas.iterator();
			while(itAlertas.hasNext()){
				Alerta alerta = (Alerta) itAlertas.next();
				Fase fase = hermod.getFase(alerta.getIdFase());
				String mensaje = "Tiene una alerta para el turno " + alerta.getIdWorkflow() + " en " + fase.getNombre() + ".";
				mensajesAlertas.add(mensaje);
			
			}
		}
		session.setAttribute(WebKeys.ALERTAS, mensajesAlertas);
		return request;
	}



	

}
