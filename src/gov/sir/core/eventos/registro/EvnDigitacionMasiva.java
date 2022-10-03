package gov.sir.core.eventos.registro;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.eventos.comun.EvnSIR;

/**
 * Evento para llamar las acciones necesarias para la fase de digitación masiva. 
 * @author ppabon
 */
public class EvnDigitacionMasiva extends EvnSIR {
	
	//	SIR-41(R1)

	/**
	 * Constante que identifica que se desea bloquear los folios mientras se realiza el proceso de corrección por el digitador.
	 */
	public final static String TOMAR_FOLIO_DIGITACION = "TOMAR_FOLIO_DIGITACION";

	/**
	 * Constante que identifica que se desea aprobar la corrección en la fase de usuario digitador.
	 */
	public final static String APROBAR_DIGITACION = "APROBAR_DIGITACION";

	/**
	 * Constante que identifica que se negar la corrección en la fase de usuario digitador.
	 */
	public final static String NEGAR_DIGITACION = "NEGAR_DIGITACION";
	
	/** 
	 * Constante para indicar que se desea realizar la consulta a un folio para cargar la información de un folio
	 * */
	public static final String CONSULTAR_FOLIO_DIGITACION_MASIVA = "CONSULTAR_FOLIO_DIGITACION_MASIVA";
	
	/** 
	 * Constante para indicar que se desea realizar el reload de la info
	 * */
	public static final String DIGITACION_MASIVA_RELOAD = "DIGITACION_MASIVA_RELOAD";
	
	/** 
	 * Constante para indicar que se desea realizar una actualización a los folios indicados
	 * */
	public static final String EDITAR_FOLIO_DIGITACION_MASIVA = "EDITAR_FOLIO_DIGITACION_MASIVA";
	
	/** 
	 * Constante para indicar que se desea realizar una actualización a los folios indicados
	 * */
	public static final String CONSTRUIR_COMPLEMENTACION = "CONSTRUIR_COMPLEMENTACION";
	
	/**
	 * Constante que identifica que se desea avanzar por EXITO
	 */
	public final static String EXITO = "EXITO";

	/**
	 * Constante que identifica que se desea avanzar por FRACASO
	 */
	public final static String FRACASO = "FRACASO";

	public static final String VER_DETALLES_FOLIO = "VER_DETALLES_FOLIO";
	
	public static final String AGREGAR_FOLIO_DIRECCION="AGREGAR_FOLIO_DIRECCION";
	public static final String ELIMINAR_FOLIO_DIRECCION="ELIMINAR_FOLIO_DIRECCION";
	public static final String ACTUALIZAR_FOLIO_COMPLEMENTACION = "ACTUALIZAR_FOLIO_COMPLEMENTACION";
	/**
	* Turno con el que se va a realizar el englobe.
	*/
	private Turno turno;

	/**
	* Folio que se desea consultar.
	*/
	private Folio folio;

	/**
	* Objeto para guardar el usuario del negocio
	*/
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	
	/**
	* Objeto para guardar oficios judiciales
	*/	
	private gov.sir.core.negocio.modelo.Oficio oficio;

	/**
	* Objeto para guardar el tipo de actualización
	*/	
	private String tipoActualizacion;

	/**
	* Objeto para guardar una lista de folios
	*/
	private java.util.List conjuntoFolios;
	
	/**
	* Objeto para guardar una lista rango anotación a aprtir de las cuáles se generará la complementación.
	*/
	private java.util.List rangoAnotaciones;
	
	private List dirTemporales = null;
	

	//CONSTRUCTORES
	/**
	* Constructor EvnDigitacionMasiva
	* @param turno Turno.
	* @param 
	*/
	public EvnDigitacionMasiva(Usuario usuario, String tipoEvento, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {

		super(usuario, tipoEvento);
		this.turno = turno;
		this.usuarioSIR = usuarioSIR;

	}
	
	/**
	* Constructor EvnDigitacionMasiva
	* @param 
	*/
	public EvnDigitacionMasiva(Usuario usuario, String tipoEvento, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {

		super(usuario, tipoEvento);
		this.usuarioSIR = usuarioSIR;

	}
	
	
	public EvnDigitacionMasiva(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	/**
	 * @return
	 */
	public java.util.List getConjuntoFolios() {
		return conjuntoFolios;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Oficio getOficio() {
		return oficio;
	}

	/**
	 * @return
	 */
	public String getTipoActualizacion() {
		return tipoActualizacion;
	}

	/**
	 * @param list
	 */
	public void setConjuntoFolios(java.util.List list) {
		conjuntoFolios = list;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @param oficio
	 */
	public void setOficio(gov.sir.core.negocio.modelo.Oficio oficio) {
		this.oficio = oficio;
	}

	/**
	 * @param string
	 */
	public void setTipoActualizacion(String string) {
		tipoActualizacion = string;
	}

	/**
	 * @return
	 */
	public java.util.List getRangoAnotaciones() {
		return rangoAnotaciones;
	}

	/**
	 * @param list
	 */
	public void setRangoAnotaciones(java.util.List list) {
		rangoAnotaciones = list;
	}

	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		this.usuarioSIR = usuarioSIR;
	}

	public List getDirTemporales() {
		return dirTemporales;
	}
	
	public void setDirTemporales(List dirTemporales) {
		this.dirTemporales=dirTemporales;
	}

}
