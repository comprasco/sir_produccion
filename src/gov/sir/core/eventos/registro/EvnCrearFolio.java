package gov.sir.core.eventos.registro;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author ppabon
 * Evento que lleva la información necesaria para la creación de un nuevo folio
 */
public class EvnCrearFolio extends EvnSIR {
    

	public static final String CREACION_FOLIO="CREACION_FOLIO";
	
	public static final String ELIMINAR_FOLIO_TEMPORAL="ELIMINAR_FOLIO_TEMPORAL";
	
	/**Constante que indica realizar las vaslidaciones del ciudadano de una anotacion*/
	public static final String VALIDAR_ANOTACION_CIUDADANO="VALIDAR_ANOTACION_CIUDADANO";	
    

    private Turno turno;
    private gov.sir.core.negocio.modelo.Usuario usuarioSir;
    private Fase fase;
    private Folio folio;
    private Circulo circulo;
	/**Lista de anotaciones agregadas de un folio. Por cada una que se agregue, se muestra
	 * el listado de anotaciones en la parte superior de la patanlla de creacion de anotaciones*/
	private List anotacionesAgregadas = null;

	/**Anotacion que se va a agregar*/
	private Anotacion anotacion = null;
	
	private boolean generarNextOrden = false;
    
    
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
	public EvnCrearFolio(Usuario usuario, String tipoEvento, Turno turno, Fase fase, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioSir) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.folio = folio;
		this.usuarioSir = usuarioSir;
	}
	
	/**
	 * @param usuario
	 * @param tipoEvento
	 * @param turno
	 * @param folio
	 * @param usuarioSir
	 */
	public EvnCrearFolio(Usuario usuario, String tipoEvento, Turno turno, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioSir) {
		super(usuario, tipoEvento);
		this.turno = turno;
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
	public Fase getFase() {
		return fase;
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
	 * @param fase
	 */
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioSir(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioSir = usuario;
	}

	public boolean isGenerarNextOrden() {
		return generarNextOrden;
	}

	public void setGenerarNextOrden(boolean generarNextOrden) {
		this.generarNextOrden = generarNextOrden;
	}

	public List getAnotacionesAgregadas() {
		return anotacionesAgregadas;
	}

	public void setAnotacionesAgregadas(List anotacionesAgregadas) {
		this.anotacionesAgregadas = anotacionesAgregadas;
	}

	public Anotacion getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(Anotacion anotacion) {
		this.anotacion = anotacion;
	}

}
