package gov.sir.core.web.listener;

import gov.sir.core.negocio.modelo.Impresion;
import gov.sir.core.web.WebKeys;
import gov.sir.print.server.dao.ImpresionDAO;
import gov.sir.print.server.dao.PrintFactory;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * @author gardila
 * 
 */
public class ImpresionSessionListener implements HttpSessionListener {

	private ImpresionDAO impresionDAO = null;

	private static Map sesiones = null;

	private static Logger logEstatico = Logger
			.getLogger(ImpresionSessionListener.class);

	public ImpresionSessionListener() throws Exception {
		ImpresionDAO imp = PrintFactory.getFactory().getImpresionDAO();
		logEstatico.info("Iniciando Listener de Sesion " + "-->impresionDAO: "
				+ imp);
		this.impresionDAO = imp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();

		if (sesiones == null) {
			sesiones = new Hashtable();
			session.getServletContext().setAttribute(WebKeys.LISTA_SESIONES,
					sesiones);
		}
		logEstatico.debug("Se crea una session " + session.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		String idSession = event.getSession().getId();

		//logger.debug("Se elimina un session " + idSession);
		//logger.debug("sesiones actuales " + sesiones.size());
		logEstatico.debug("sesiones actuales " + sesiones.size());
		logEstatico.debug("Se elimina un session " + idSession);
		HttpSession session = event.getSession();
		sesiones = (Map) session.getServletContext().getAttribute(
				WebKeys.LISTA_SESIONES);
		if( sesiones!=null ){
			sesiones.remove(idSession);
		}

		try {
			if (this.impresionDAO != null) {
				Impresion impresion = new Impresion();
				impresion.setIdSesion(idSession);
				this.impresionDAO.deleteSesionImpresion(impresion);
			}
		} catch (Throwable e) {
			logEstatico
					.error("Fallo en el DAO de impresion al eliminar una session : "
							+ e.getMessage());
			logEstatico.error(e);
		}
	}

}
