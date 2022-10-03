package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Folio;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author ppabon
 * Evento que lleva la información necesaria para la creación de un nuevo folio
 */
public class EvnCrearFolio extends EvnSIR {
	
	
	boolean updating;
    

	public static final String CREACION_FOLIO="CREACION_FOLIO";
	public static final String VALIDAR_MATRICULA_CREACION_FOLIO = "VALIDAR_MATRICULA_CREACION_FOLIO";
	public static final String AGREGAR_ANOTACION = "AGREGAR_ANOTACION";
	public static final String CREAR_CANCELACION = "CREAR_CANCELACION";
	public static final String CANCELAR_ANOTACIONES = "CANCELAR_ANOTACIONES";
	public static final String CREAR_SEGREGACION_ENGLOBE = "CREAR_SEGREGACION_ENGLOBE";
	public static final String CREAR_DERIVACION = "CREAR_DERIVACION";
	public static final String ELIMINAR_DERIVACION = "ELIMINAR_DERIVACION";
	public static final String CREAR_ENGLOBE = "CREAR_ENGLOBE";
	public static final String ELIMINAR_ENGLOBE = "ELIMINAR_ENGLOBE";
	
	public static final String VOLVER_EDICION = "VOLVER_EDICION";
	
	public static final String ELIMINAR_ANOTACION = "ELIMINAR_ANOTACION";
	
	public static final String GRABAR_ANOTACIONES_TEMPORALMENTE = "GRABAR_ANOTACIONES_TEMPORALMENTE";
	
	public static final String AGREGAR_CIUDADANO_ANOTACION ="AGREGAR_CIUDADANO_ANOTACION";
    
	public static final String IMPRIMIR_HOJA_DE_RUTA = "IMPRIMIR_HOJA_DE_RUTA";
	
	public static final String GUARDAR_FOLIO = "GUARDAR_FOLIO";
	
    private gov.sir.core.negocio.modelo.Usuario usuarioSir;
    private Folio folio;
    private Circulo circulo;
    private DatosAntiguoSistema datosAntiguoSistema;
    private Anotacion anotacion;
	/**Lista de anotaciones agregadas de un folio. Por cada una que se agregue, se muestra
	 * el listado de anotaciones en la parte superior de la patanlla de creacion de anotaciones*/
	private List anotacionesAgregadas = null;
	
	private boolean generarNextOrden = false;
    private Anotacion anotacionHijo;
    private Anotacion anotacionPadre;
    
    private Folio folioPadre;
    private Folio folioHijo;
    
    private List anotacionCiudadanos;
	
	private List listaCompletaCiudadanos;
	
	private List anotacionesGuardar;
	
	private String uid;
    
    
    /**
	 * @param usuario
	 */
	public EvnCrearFolio(Usuario usuario) {
        super(usuario);
    }

    /**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnCrearFolio(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
    }
    
    
	/**
	 * @param usuario
	 * @param tipoEvento
	 * @param turno
	 * @param fase
	 * @param folio
	 * @param usuarioSir
	 */
	public EvnCrearFolio(Usuario usuario, String tipoEvento, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioSir) {
		super(usuario, tipoEvento);
		this.folio = folio;
		this.usuarioSir = usuarioSir;
	}
	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
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
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSir() {
		return usuarioSir;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}


	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioSir(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioSir = usuario;
	}

	public void setDatosAntiguoSistema(DatosAntiguoSistema datosAntiguoSistema) {
		this.datosAntiguoSistema = datosAntiguoSistema;
	}

	public DatosAntiguoSistema getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}
	
		public boolean isUpdating() {
		return updating;
	}

	public void setUpdating(boolean editing) {
		this.updating = editing;
	}

	public Anotacion getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(Anotacion anotacion) {
		this.anotacion = anotacion;
	}
	public Anotacion getAnotacionHijo() {
		return anotacionHijo;
	}

	public void setAnotacionHijo(Anotacion anotacionHijo) {
		this.anotacionHijo = anotacionHijo;
	}

	public Anotacion getAnotacionPadre() {
		return anotacionPadre;
	}

	public void setAnotacionPadre(Anotacion anotacionPadre) {
		this.anotacionPadre = anotacionPadre;
	}

	public Folio getFolioHijo() {
		return folioHijo;
	}

	public void setFolioHijo(Folio folioHijo) {
		this.folioHijo = folioHijo;
	}

	public Folio getFolioPadre() {
		return folioPadre;
	}

	public void setFolioPadre(Folio folioPadre) {
		this.folioPadre = folioPadre;
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
	
	public List getAnotacionesGuardar() {
		return anotacionesGuardar;
	}

	public void setAnotacionesGuardar(List anotacionesGuardar) {
		this.anotacionesGuardar = anotacionesGuardar;
	}

	public List getAnotacionesAgregadas() {
		return anotacionesAgregadas;
	}

	public void setAnotacionesAgregadas(List anotacionesAgregadas) {
		this.anotacionesAgregadas = anotacionesAgregadas;
	}

	public boolean isGenerarNextOrden() {
		return generarNextOrden;
	}

	public void setGenerarNextOrden(boolean generarNextOrden) {
		this.generarNextOrden = generarNextOrden;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
