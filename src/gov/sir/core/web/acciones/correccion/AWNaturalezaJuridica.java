package gov.sir.core.web.acciones.correccion;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

public class AWNaturalezaJuridica extends SoporteAccionWeb {
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION = "ANOTACION_NAT_JURIDICA_ESPECIFICACION";
	
	public static final String ANOTACION_FECHA_RADICACION = "ANOTACION_FECHA_RADICACION";
	
	public static final String FECHA_NUEVAS_NATURALEZAS_STR = "03/07/2001";
	public static Date FECHA_NUEVAS_NATURALEZAS = new Date();
	
	public static final String ANOTACION_MODICADA = "ANOTACION_MODICADA";

        public static final String FOLIO_FECHA_APERTURA_NJ = "FOLIO_FECHA_APERTURA_NJ";
	
	static {

            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                FECHA_NUEVAS_NATURALEZAS = formatoDelTexto.parse(FECHA_NUEVAS_NATURALEZAS_STR);
            } catch (ParseException ex) {
                Logger.getLogger(AWNaturalezaJuridica.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	/** Aca se guarda la session global para la clase*/
	private HttpSession session;

	/** Variable donde se guarda la accion enviada en el request*/
	private String accion;

	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		session = request.getSession();
		accion = request.getParameter(WebKeys.ACCION);
		
		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion válida");
		}
           
		else if (accion.equals(ANOTACION_NAT_JURIDICA_ESPECIFICACION)) {
			return this.mostrarPantallaNaturalezasJuridicas(request);	
		}else {
			throw new AccionInvalidaException("La accion " + accion + " no es válida.");
		}
	}
	
	protected Evento mostrarPantallaNaturalezasJuridicas(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		request.getSession().setAttribute(WebKeys.ACTUALIZAR, new Boolean(true));
		
		Date fechaRad = null;
		String fechaRadStr = request.getParameter(AWModificarFolio.ANOTACION_FECHA_RADICACION);
		if (fechaRadStr != null && !fechaRadStr.equals(""))
		{
			String []fechaSplit = fechaRadStr.split("/");
			if (fechaSplit.length == 3)
			{
				if ((fechaSplit[0].length() == 2 || fechaSplit[0].length() == 1)
						&& (fechaSplit[1].length() == 2 || fechaSplit[1].length() == 1)
						&& fechaSplit[2].length() == 4)
				{
					Calendar cal = Calendar.getInstance();
					int dia = Integer.parseInt(fechaSplit[0]);
					int mes = Integer.parseInt(fechaSplit[1]);
					int anio = Integer.parseInt(fechaSplit[2]);
					
					cal.set(Calendar.DAY_OF_MONTH, dia);
					cal.set(Calendar.MONTH, mes-1);
					cal.set(Calendar.YEAR, anio);
					
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.MILLISECOND, 0);
					
					fechaRad = cal.getTime();
				}
			}
		}
		request.getSession().setAttribute(ANOTACION_FECHA_RADICACION, fechaRad);
		
		//se obtiene el id de la naturaleza jurídica 
		//antes de la modificación, y se envia al helper 
		Anotacion anotPrev = (Anotacion)request.getSession().getAttribute(AWModificarFolio.ANOTACION_EDITADA);
		request.getSession().setAttribute(ANOTACION_MODICADA, anotPrev);
		
		return null;
	}
	

}
