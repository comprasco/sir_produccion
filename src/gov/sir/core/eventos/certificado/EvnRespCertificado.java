package gov.sir.core.eventos.certificado;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.PlantillaPertenencia;

/**
 * Este evento de respuesta trae información sobre certificados, de la capa de negocio
 * @author dcantor, dsalas, mmunoz
 */
public class EvnRespCertificado extends EvnSIRRespuesta{

	
	/**El folio que se asocia al turno cuando es de antiguo sistema*/ 
	private Folio folio;

	/**La liquidacion que se asocia al turno del turno anterior*/ 
	private Liquidacion liquidacion;
	
	/**
	* El número de secuencial de recibo asociado con la estación.  
	*/
	private long secuencialRecibo;
	
	/**
	* Lista para mantener las notas informativas de la fase de
	* arranque
	*/
	private List listaNotasInformativas;
	
	
	/**
	 * Numero de anotaciones asociadas con el folio
	 */
	private String numeroAnotaciones;
	
	/**
	* Cadena que indica si el folio es de mayor extension
	*/
	private String mayorExtension;
	
	String matricula;
	
	
	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String FOLIO_EXISTENTE = "FOLIO_EXISTENTE";
	
	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TURNO_EXISTENTE = "TURNO_EXISTENTE";
	
	/**Esta constante define el tipo evento que se envia a la accion de negocio,cuándo imprimió la huja de ruta. */
	public static final String IMPRIMIR_HOJA_RUTA = "IMPRIMIR_HOJA_RUTA";
	
	/**Esta constante define el tipo evento que se envia a la accion de negocio, cuando se quiere consultar un folio para copiar su complementación. */
	public static final String CONSULTA_FOLIO_COMPLEMENTACION = "CONSULTA_FOLIO_COMPLEMENTACION";
	
	/**Esta constante define el tipo evento que se envia a la accion de negocio, cuando se quiere consultar una plantilla
	 * para el oficio de pertenencia. */
	public static final String CONSULTAR_PLANTILLA_PERTENENCIA= "CONSULTAR_PLANTILLA_PERTENENCIA";
	
	/**
	* Constante que indica que se envía un secuencial de recibo. 
	*/
	public static final String ADMINISTRACION_RECIBOS = "ADMINISTRACION_RECIBOS";

	public static final String OBTENER_FOLIO_REIMPRESION_ESPECIAL = "OBTENER_FOLIO_REIMPRESION_ESPECIAL";
	
	public static final String VALIDACION_MATRICULA_MIG = "VALIDACION_MATRICULA_MIG";
	
	public static final String ACTUALIZAR_TRADICION_FOLIO="ACTUALIZAR_TRADICION_FOLIO";
	
	public static final String CER_AMPLIACION_TRADICION="CER_AMPLIACION_TRADICION";
	
	
	
	/**
	 * Constructor del evento de respuesta con parametros.
	 * Este evento se retorna cuando se relaciona un folio con el turno. 
	 * @param folio Folio
	 */
	public EvnRespCertificado(Folio folio) {
		super(folio,FOLIO_EXISTENTE);
		this.folio = folio;
	}

	/**
	 * Constructor del evento de respuesta con el tipo de respuesta unicamente.
	 * @param folio Folio
	 */
	public EvnRespCertificado(String tipoEvento) {
		super(tipoEvento,tipoEvento);
	}


	/**
	 * Constructor del evento de respuesta con el tipo de respuesta y el folio, es usado para consultar un folio
	 * y de esta manera poder copiar su complementación.
	 * @param folio Folio
	 */
	public EvnRespCertificado(String tipoEvento, Folio folio) {
		super(tipoEvento,tipoEvento);
		this.folio = folio;
	}


	/**
	 * Constructor del evento de respuesta con el tipo de respuesta y la plantilla que se colocará
	 * por defecto en la genenración del oficio de pertenencia.
	 * @param folio Folio
	 */
	public EvnRespCertificado(String tipoEvento, PlantillaPertenencia plantilla) {
		super(plantilla,tipoEvento);
	}


	/**
	 * Constructor del evento de respuesta con parametros.
	 * Este evento se retorna cuando se relaciona una liquidacion con el turno. 
	 * @param liquidacion Liquidacion
	 */
	public EvnRespCertificado(Liquidacion liquidacion) {
		super(liquidacion, TURNO_EXISTENTE);
		this.liquidacion = liquidacion;
	}
	
	
	/**
	* Constructor del evento de respuesta con parametros.
	* Este evento se retorna cuando se relaciona un secuencial de recibo,
	* y se desea cargar las notas informativas de la fase inicial.  
	* @param secuencial Número del secuencial de recibo. 
	* @param notasInformativas Lista con las notas informativas asociadas
	* a esta fase.
	*/
	 public EvnRespCertificado(long secuencial, List notasInformativas) {
			super(""+secuencial,EvnRespCertificado.ADMINISTRACION_RECIBOS);
			this.secuencialRecibo = secuencial;
			this.listaNotasInformativas = notasInformativas;
	}
	 
	public EvnRespCertificado(String matricula, String tipoRespuesta){
		super(tipoRespuesta);
		this.matricula = matricula;
	}

	/**
	 * Metodo que retorna el folio que viaja en el evento
	 * @return Folio
	 */
	public Folio getFolio() {
		return folio;
	}
	
	
	
	/**
	 * Metodo que retorna la liquidacion que viaja en el evento
	 * @return Liquidacion
	 */
	public Liquidacion getLiquidacion() {
		return liquidacion;
	}
	
	
	/**
	* Metodo que retorna el secuencial de recibo que viaja en el evento. 
	* @return Folio
	*/
   public long getSecuencialRecibo() {
			return this.secuencialRecibo;
		}
		
		
	/**
	* Metodo que retorna la lista de las notas informativas asociadas
	* con el proceso de solicitar un certificado. 
	* @return Lista de notas informativas.
	*/
   public List getListaNotasInformativas() {
			return this.listaNotasInformativas;
		}

	/**
	 * @return
	 */
	public String getMayorExtension() {
		return mayorExtension;
	}

	/**
	 * @return
	 */
	public String getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	/**
	 * @param string
	 */
	public void setMayorExtension(String string) {
		mayorExtension = string;
	}

	/**
	 * @param string
	 */
	public void setNumeroAnotaciones(String string) {
		numeroAnotaciones = string;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
