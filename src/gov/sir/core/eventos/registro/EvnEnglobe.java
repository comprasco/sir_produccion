package gov.sir.core.eventos.registro;

import java.util.ArrayList;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.WebEnglobe;
import gov.sir.core.eventos.comun.EvnSIR;

/**
 * Evento para llamar las acciones necesarias para el proceso de englobe. 
 * @author dlopez
 */
public class EvnEnglobe extends EvnSIR {

	/** Constante para llamar a la acción que guarda los folios que haran parte del englobe*/
	public final static String SELECCIONAR_FOLIOS = "SELECCIONAR_FOLIOS";
	
	/** Constante para llamar a la acción que guarda los datos relacionados con el objeto WebEnglobe*/
	public final static String GUARDAR_WEB_ENGLOBE = "GUARDAR_WEB_ENGLOBE";	

	/** Constante para llamar a la acción que guarda una anotación en el objeto WebEnglobe*/
	public final static String CONSULTA_FOLIO = "CONSULTA_FOLIO";

	/** Constante para llamar a la acción que guarda una anotación en el objeto WebEnglobe*/
	public final static String CONSULTA_ANOTACIONES_TEMPORALES = "CONSULTA_ANOTACIONES_TEMPORALES";

	/** Constante para llamar a la acción que guarda una anotación en el objeto WebEnglobe*/
	public final static String ENGLOBAR_FOLIOS = "ENGLOBAR_FOLIOS";

	/** Constante para llamar a la acción que elimina un objeto WebEnglobe. Es decir un proceso de englobe en curso.*/
	public final static String ELIMINAR_ENGLOBE = "ELIMINAR_ENGLOBE";
	
	public static final String CONSULTAR_ULTIMOS_PROPIETARIOS ="CONSULTAR_ULTIMOS_PROPIETARIOS";
	
	public static final String AGREGAR_CIUDADANO_ANOTACION ="AGREGAR_CIUDADANO_ANOTACION";


	/**
	* Turno con el que se va a realizar el englobe.
	*/
	private Turno turno;

	/**
	* Folio que se desea consultar.
	*/
	private Folio folio;

	/**
	* Objeto donde se almacenará toda la información referente a la solicitud de englobe
	*/
	private WebEnglobe webEnglobe;	
	
	/**
	* Objeto donde se almacenará toda la información referente al circulo
	*/
	private Circulo circulo;		
	

	/**
	* Objeto para guardar el usuario del negocio
	*/
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;

	/**
	* Lista que guarda los folios que van a ser englobados. 
	*/
	private List foliosEnglobados = new ArrayList();
	
	private List anotacionCiudadanos;
	
	private List listaCompletaCiudadanos;
	
	private List listaCompletaEjes;
	
	private List direccionesEnglobe;

        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se agregan las propiedades salvedadDescripcion y salvedadId y sus metodos get y set
         *                  para manejar la salvedad
         * Caso Mantis  :   04131
         */
        private String salvedadDescripcion;

        private String salvedadId;

        public String getSalvedadDescripcion() {
            return salvedadDescripcion;
        }

        public void setSalvedadDescripcion(String salvedadDescripcion) {
            this.salvedadDescripcion = salvedadDescripcion;
        }

        public String getSalvedadId() {
            return salvedadId;
        }

        public void setSalvedadId(String salvedadId) {
            this.salvedadId = salvedadId;
        }
        
	//CONSTRUCTORES
	/**
	* Constructor EvnEnglobe
	* @param turno Turno asociado con el proceso de englobe.
	* @param 
	*/
	public EvnEnglobe(Usuario usuario, String tipoEvento, Turno turno, WebEnglobe webEnglobe, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {

		super(usuario, tipoEvento);
		this.turno = turno;
		this.webEnglobe = webEnglobe;
		this.usuarioNeg = usuarioNeg;
		
	}
	
	/**
	* Constructor EvnEnglobe
	* @param turno Turno asociado con el objeto WebEnglobe.
	* @param 
	*/	
	public EvnEnglobe(Usuario usuario, String tipoEvento, Turno turno, Circulo circulo ,  WebEnglobe webEnglobe, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {

		super(usuario, tipoEvento);
		this.turno = turno;
		this.circulo = circulo;
		this.webEnglobe = webEnglobe;
		this.usuarioNeg = usuarioNeg;
		
	}	
	

	/**
	 * @param usuario
	 * @param folio
	 * @param tipoEvento
	 */
	public EvnEnglobe(Usuario usuario, Folio folio, String tipoEvento) {
		super(usuario, tipoEvento);
		this.folio = folio;
	}

	/**
	 * @param usuario
	 * @param folio
	 * @param tipoEvento
	 * @param usuarioNeg
	 */
	public EvnEnglobe(Usuario usuario, Folio folio, WebEnglobe webEnglobe , String tipoEvento, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, tipoEvento);
		this.folio = folio;
		this.webEnglobe = webEnglobe;
		this.usuarioNeg = usuarioNeg;
	}
	
	public EvnEnglobe(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	//MODIFICADORES

	/**
	* Asigna al atributo usuarioNeg el  <code>Usuario</code> de negocio asociado con el proceso de englobe.
	* @param  usuario el <code>Usuario</code> de negocio asociado con el proceso de englobe.
	*/
	public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuario) {
		this.usuarioNeg = usuario;
	}

	/**
	* Asigna al atributo turno, el   <code>Turno</code> asociado con el proceso de englobe.
	* @param  turno el <code>Turno</code> asociado con el proceso de englobe.
	*/
	public void setTurno(Turno turno) {
		this.turno = turno;
	}


	/**
		* Asocia a la lista de  folios que van a ser englobados durante el proceso, la lista recibida como
		* parámetro.  
		* @param la lista de  folios que van a ser englobados durante el proceso que se asociará al atributo
		* foliosEnglobados 
		*/
	public void setFoliosEnglobados(List folios) {
		this.foliosEnglobados = folios;
	}

	//ANALIZADORES

	/**
	* Retorna el <code>Usuario</code> de negocio asociado con el proceso de englobe.
	* @return El <code>Usuario</code> de negocio  asociado con el proceso de englobe.
	*/
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}

	/**
	 * Retorna el <code>Turno</code> asociado con el proceso de englobe.
	 * @return El <code>Turno</code> asociado con el proceso de englobe.
	 */
	public Turno getTurno() {
		return turno;
	}


	/**
	 * Retorna la lista de  folios que van a ser englobados durante el proceso. 
	 * @return la lista de  folios que van a ser englobados durante el proceso. 
	 */
	public List getFoliosEnglobados() {
		return foliosEnglobados;
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
	public WebEnglobe getWebEnglobe() {
		return webEnglobe;
	}

	/**
	 * @param englobe
	 */
	public void setWebEnglobe(WebEnglobe englobe) {
		webEnglobe = englobe;
	}

	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
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

	public List getListaCompletaEjes() {
		return listaCompletaEjes;
	}

	public void setListaCompletaEjes(List listaCompletaEjes) {
		this.listaCompletaEjes = listaCompletaEjes;
	}

	public List getDireccionesEnglobe() {
		return direccionesEnglobe;
	}

	public void setDireccionesEnglobe(List direccionesEnglobe) {
		this.direccionesEnglobe = direccionesEnglobe;
	}

}
