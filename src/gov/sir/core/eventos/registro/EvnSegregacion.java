package gov.sir.core.eventos.registro;

import java.util.Date;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;

/**
 * Evento para llamar las acciones necesarias para el proceso de segregación.
 * @author ppabon
 */
public class EvnSegregacion extends EvnSIR {
	
	/** Constante para llamar a la acción que guarda una anotación en el objeto WebSegregacion*/
	public final static String CONSULTA_FOLIO = "CONSULTA_FOLIO";
	
	/** Constante para llamar a la acción que guarda los cambios realizados en la segregación*/
	public final static String GUARDAR_WEB_SEGREGACION = "GUARDAR_WEB_SEGREGACION";
	
	/** Constante para llamar a la acción que guarda una anotación en el objeto WebSegregacion*/
	public final static String CONSULTA_ANOTACIONES_TEMPORALES = "CONSULTA_ANOTACIONES_TEMPORALES";		
	
	/** Constante para llamar a la acción que guarda una anotación en el objeto WebSegregacion*/
	public final static String SEGREGACION_FOLIO = "SEGREGACION_FOLIO";		
	
	/** Constante para llamar a la acción que consulta la información de los nuevos folios segregados*/
	public final static String CONSULTAR_NUEVO_FOLIO = "CONSULTAR_NUEVO_FOLIO";
	
	/** Constante para llamar a la acción que guarda los cambios realizados a los folios segregados*/
	public final static String GUARDAR_CAMBIOS_FOLIO = "GUARDAR_CAMBIOS_FOLIO";
	
	/** Constante para llamar a la acción que elimina un objeto WebSegregacion. Es decir un proceso de segregacion en curso.*/
	public final static String ELIMINAR_SEGREGACION = "ELIMINAR_SEGREGACION";	
	
	public static final String CONSULTAR_ULTIMOS_PROPIETARIOS ="CONSULTAR_ULTIMOS_PROPIETARIOS";
	
	public static final String AGREGAR_CIUDADANO_ANOTACION ="AGREGAR_CIUDADANO_ANOTACION";
	
	

				

	/**
	* Turno con el que se va a realizar la segregación  
	*/
	private Turno turno;

	/**
    * Folio del que se va a obtener el siguiente orden de anotación  
    */
	private Folio folio;
	
	/**
	* Clase donde se almacenará toda la información referente a la segregación
	*/
	private WebSegregacion webSegregacion;	
	
	/**
	* Usuario para guardar el usuario del negocio
	*/
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;	
	
	/**
	* Circulo de la oficina
	*/
	private Circulo circulo;		
	
	/**
	* Lista de 
	*/	
	private List anotacionCiudadanos;
	
	private List listaCompletaCiudadanos;
	
	private String numeroRadicacion;
	
	private Date fechaAnotacion;

	/*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se agrega la propiedad SalvedadAnotacion salvedadanotacion y sus metodos get y set
         *                  para manejar la salvedad
         * Caso Mantis  :   04131
         */
        private SalvedadAnotacion salvedadanotacion;

        public SalvedadAnotacion getSalvedadanotacion() {
            return salvedadanotacion;
        }

        public void setSalvedadanotacion(SalvedadAnotacion salvedadanotacion) {
            this.salvedadanotacion = salvedadanotacion;
        }     

	/**
	 * @param usuario
	 * @param folio
	 * @param tipoEvento
	 */
	public EvnSegregacion(Usuario usuario, Folio folio, String tipoEvento) {
		super(usuario, tipoEvento);
		this.folio = folio;
	}    
	
	/**
	 * @param usuario
	 * @param folio
	 * @param tipoEvento
	 * @param usuarioNeg
	 */
	public EvnSegregacion(Usuario usuario, Folio folio, Turno turno , WebSegregacion webSegregacion , String tipoEvento, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, tipoEvento);
		this.folio = folio;
		this.turno = turno;
		this.webSegregacion = webSegregacion;
		this.usuarioNeg = usuarioNeg;
	}    	

	/**
	 * @param usuario
	 * @param folio 
	 * @param tipoEvento
	 */
	public EvnSegregacion(Usuario usuario, Folio folio,  WebSegregacion webSegregacion, Turno turno, Circulo circulo , String tipoEvento, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.circulo = circulo;
		this.webSegregacion = webSegregacion;
		this.usuarioNeg = usuarioNeg;
		this.folio = folio;
	}    	
	
	/**
	 * @param usuario
	 * @param folio 
	 * @param tipoEvento
	 */
	public EvnSegregacion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Turno turno,  Folio folio, String tipoEvento ) {
		super(usuario, tipoEvento);
		this.usuarioNeg = usuarioNeg;
		this.folio = folio;
		this.turno = turno;
	}    
	
	public EvnSegregacion(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	public Date getFechaAnotacion() {
		return fechaAnotacion;
	}

	public void setFechaAnotacion(Date fechaAnotacion) {
		this.fechaAnotacion = fechaAnotacion;
	}

	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	/**
	 * @return folio
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}
	
	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @return
	 */
	public WebSegregacion getWebSegregacion() {
		return webSegregacion;
	}

	/**
	 * @param segregacion
	 */
	public void setWebSegregacion(WebSegregacion segregacion) {
		webSegregacion = segregacion;
	}

	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}

	public List getAnotacionCiudadanos() {
		return anotacionCiudadanos;
	}

	public void setAnotacionCiudadanos(List anotacionCiudadanos) {
		this.anotacionCiudadanos = anotacionCiudadanos;
	}

	public List getListaCompletaCiudadanos() {
		return listaCompletaCiudadanos;
	}

	public void setListaCompletaCiudadanos(List listaCompletaCiudadanos) {
		this.listaCompletaCiudadanos = listaCompletaCiudadanos;
	}

}
