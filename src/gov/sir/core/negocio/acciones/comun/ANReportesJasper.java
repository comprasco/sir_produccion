package gov.sir.core.negocio.acciones.comun;

import gov.sir.core.eventos.comun.EvnReportesJasper;
import gov.sir.core.eventos.comun.EvnRespReporteJasper;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

public class ANReportesJasper extends SoporteAccionNegocio {

	/**
	 * Encargado de mantener los logs utilizando log4j 
	 */
	private static Logger logger = Logger.getLogger(ANCiudadano.class);
	
	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;
	
	public ANReportesJasper () throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

	}
	
	
	public EventoRespuesta perform(Evento ev) throws EventoException {
		// TODO Auto-generated method stub
		String tipoEvento = ev.getTipoEvento();
		
		if (tipoEvento != null) {
			if (tipoEvento.equals(EvnReportesJasper.GENERAR_PDF_REPORTE)) {
				return generarPdfReporte((EvnReportesJasper)ev);
			}
		}
		return null;
	}


	private EventoRespuesta generarPdfReporte(EvnReportesJasper ev) 
		throws EventoException{
		String nombreReporte = ev.getNombreReporte();
		HashMap parametrosJasper = ev.getParametros();
		
		
		
		String rutaReportes = HermodProperties.getInstancia().getProperty(HermodProperties.RUTA_REPORTES_JASPER);
		parametrosJasper.put("SUBREPORT_DIR", rutaReportes);
		EvnRespReporteJasper evnResp = null;
		try {
			
			byte []pdfBytes = hermod.generarReporteJasper(nombreReporte, rutaReportes, parametrosJasper);
			
			
			evnResp = new EvnRespReporteJasper(pdfBytes, EvnRespReporteJasper.GENERAR_EVENTO);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			logger.error("Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException("Error generando reporte", e);
		}
		
		return evnResp;
	}

}
