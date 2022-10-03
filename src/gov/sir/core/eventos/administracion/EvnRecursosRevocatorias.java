package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con la administración de los recursos y revocatorias.
 * @author ppabon
 */
public class EvnRecursosRevocatorias extends EvnSIR {

	/** Constante que identifica la acción de consultar el turno al que se le desea bloquear sus folios*/
	public static final String SELECCIONAR_TURNO_BLOQUEO = "SELECCIONAR_TURNO_BLOQUEO";
	
	/** Constante que identifica la acción de agregar matrículas para ser bloqueadas*/
	public static final String AGREGAR_MATRICULA_BLOQUEO = "AGREGAR_MATRICULA_BLOQUEO";
	
	/** Constante que identifica la acción de eliminar matrículas que querían ser bloqueadas*/
	public static final String ELIMINAR_MATRICULA_BLOQUEO = "ELIMINAR_MATRICULA_BLOQUEO";	

	/** Constante que identifica la acción de bloquear recursos*/
	public static final String BLOQUEAR_RECURSOS = "BLOQUEAR_RECURSOS";
	
	/** Constante que identifica la acción de consultar los turnos bloqueados*/
	public static final String CONSULTAR_TURNOS_BLOQUEADOS = "CONSULTAR_TURNOS_BLOQUEADOS";

	/** Constante que identifica la acción de reanotar los recursos y revocatorias directas*/
	public static final String REANOTAR_RECURSOS = "REANOTAR_RECURSOS";

	/** Constante que identifica la acción de rechazar los recursos y revocatorias directas*/
	public static final String RECHAZAR_RECURSOS = "RECHAZAR_RECURSOS";	

	private Turno turno;

	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	
	private Circulo circulo;
	
	private Folio folio;
	
	private List folios;
	
	private String justificacion;
	
	private Estacion estacion;


        /*
        * @author      : Julio Alcázar Rivas
        * @change      : se agrega la propiedad EstacionTest con sus metodos GET y SET.
        * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
        */
        private Estacion EstacionTest;

        public Estacion getEstacionTest() {
            return EstacionTest;
        }

        public void setEstacionTest(Estacion EstacionTest) {
            this.EstacionTest = EstacionTest;
        }    	

	/**
	 * Constructor para el envio de datos a la capa de negocio con el turno incluido
	 * @param usuario
	 * @param tipoEvento
	 * @param turno
	 * @param usuarioSIR
	 */
	public EvnRecursosRevocatorias(Usuario usuario, String tipoEvento, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.usuarioSIR = usuarioSIR;
	}
	
	
	/**
	 * Constructor para el envio de datos a la capa de negocio con el turno incluido
	 * @param usuario
	 * @param tipoEvento
	 * @param turno
	 * @param estacion
	 * @param justificacion
	 * @param usuarioSIR
	 */
	public EvnRecursosRevocatorias(Usuario usuario, String tipoEvento, Turno turno, Estacion estacion , String justificacion ,  gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.estacion = estacion;
		this.turno=turno;
		this.justificacion = justificacion;
		this.usuarioSIR = usuarioSIR;
	}	
	

	/**
	 * @param usuario
	 * @param tipoEvento
	 * @param turno
	 * @param folio
	 * @param usuarioSIR
	 */
	public EvnRecursosRevocatorias(Usuario usuario, String tipoEvento, Turno turno, Folio folio , gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.folio = folio;
		this.usuarioSIR = usuarioSIR;
	}	
	
	/**
	 * @param usuario
	 * @param tipoEvento
	 * @param turno
	 * @param folios
	 */
	public EvnRecursosRevocatorias(Usuario usuario, String tipoEvento, Turno turno, List folios, String justificacion, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.folios = folios;
		this.justificacion = justificacion;
		this.usuarioSIR = usuarioSIR;
	}		

	/**
	 * Constructor para el envio de datos a la capa de negocio con la acción a ejecutar y el usuario
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnRecursosRevocatorias(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
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
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
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
	public List getFolios() {
		return folios;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @param list
	 */
	public void setFolios(List list) {
		folios = list;
	}

	/**
	 * @return
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * @param string
	 */
	public void setJustificacion(String string) {
		justificacion = string;
	}

	/**
	 * @return
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * @param estacion
	 */
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

}
