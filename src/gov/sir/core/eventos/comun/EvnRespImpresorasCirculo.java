package gov.sir.core.eventos.comun;

import java.util.List;
import java.util.Map;

import gov.sir.core.negocio.modelo.Imprimible;


/**
 * @author ppabon
 */
public class EvnRespImpresorasCirculo extends EvnSIRRespuesta{

	public final static String DESCARGAR_IMPRIMIBLE = "DESCARGAR_IMPRIMIBLE";
	public final static String CONSULTAR_IMPRESIONES_FALLIDAS = "CONSULTAR_IMPRESIONES_FALLIDAS";
	public final static String DISMINUIR_NUMERO_IMPRESIONES = "DISMINUIR_NUMERO_IMPRESIONES";
	public final static String CONSULTAR_LISTA_REGLAS= "CONSULTAR_LISTA_REGLAS";
	public final static String CONSULTAR_CONFIGURACION= "CONSULTAR_CONFIGURACION";
	public final static String CONSULTAR_PARAMETROS= "CONSULTAR_PARAMETROS";
	/**
	 * Constructor del evento con parametros.
	 * @param departamentos List
	 * @param circulo gov.sir.core.negocio.modelo.Circulo
	 */
	public EvnRespImpresorasCirculo(Imprimible imprimible,  String tipoEvento) {
		super(imprimible, tipoEvento);
	}
	
	/**
	 * Constructor del evento con parametros.
	 * @param departamentos List
	 * @param circulo gov.sir.core.negocio.modelo.Circulo
	 */
	public EvnRespImpresorasCirculo(List idImpresionesFallidas,  String tipoEvento) {
		super(idImpresionesFallidas, tipoEvento);
	}	
	
	
	/**
	 * Constructor del evento con la respuesta de la actualización.
	 * @param departamentos List
	 * @param circulo gov.sir.core.negocio.modelo.Circulo
	 */
	public EvnRespImpresorasCirculo(Boolean actualizacion,  String tipoEvento) {
		super(actualizacion, tipoEvento);
	}		
	
	
	/**
	 * Constructor del evento con parametros.
	 * @param departamentos List
	 * @param circulo gov.sir.core.negocio.modelo.Circulo
	 */
	public EvnRespImpresorasCirculo(Map configuracion,  String tipoEvento) {
		super(configuracion, tipoEvento);
	}



}
