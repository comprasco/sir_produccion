package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.core.modelo.transferObjects.Rol;
/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con el traslado de turnos entre usuarios
 * @author jmendez
 */
public class EvnTrasladoTurno extends EvnSIR {

	/**Esta constante se utiliza para identificar el evento de consulta de turnos y usuarios por círculo */
	public static final String CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO =
		"CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO";

	/**Esta constante se utiliza para identificar el evento de consulta de turnos por identificador */
	public static final String CONSULTAR_TURNO_POR_IDENTIFICADOR = "CONSULTAR_TURNO_POR_IDENTIFICADOR";

	/**Esta constante se utiliza para identificar el evento de consulta de turnos por número de matrícula */
	public static final String CONSULTAR_TURNO_POR_MATRICULA = "CONSULTAR_TURNO_POR_MATRICULA";
	
	/**Esta constante se utiliza para identificar el evento de consulta de turnos por número de matrícula */
	public static final String CONSULTAR_TURNO_CERTIFICADO_CAMBIO_MATRICULA = "CONSULTAR_TURNO_CERTIFICADO_CAMBIO_MATRICULA";
    
    public static final String CONSULTAR_TURNO_POR_MATRICULA_CIRCULO_PROCESO = "CONSULTAR_TURNO_POR_MATRICULA_CIRCULO_PROCESO";

    public static final String CONSULTAR_TURNO_POR_MATRICULA_CIRCULO = "CONSULTAR_TURNO_POR_MATRICULA_CIRCULO";
    
    public static final String ANULAR_TURNO = "ANULAR_TURNO";
    
    public static final String HABILITAR_TURNO = "HABILITAR_TURNO";
    
    public static final String CANCELAR_CAMBIO_MATRICULA = "CANCELAR_CAMBIO_MATRICULA";
    
    public static final String VALIDAR_CIUDADANO_EDICION = "VALIDAR_CIUDADANO_EDICION";
    
    public static final String RELIZAR_CIUDADANO_EDICION = "RELIZAR_CIUDADANO_EDICION";
    
    public static final String REANOTAR_TURNO_ESPECIFICACION = "REANOTAR_TURNO_ESPECIFICACION";
    
    public static final String REANOTAR_TURNO = "REANOTAR_TURNO";
       /*
        * @author : CTORRES
        * @change : Se agregan las constantes VER_DETALLES_TURNO_TESTAMENTO y CARGAR_TESTAMENTO_OK
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
    public static final String VER_DETALLES_TURNO_TESTAMENTO ="VER_DETALLES_TURNO_TESTAMENTO";
    
    public static final String CARGAR_TESTAMENTO_OK = "CARGAR_TESTAMENTO_OK";

        private List canalRecaudoYcuentas;

	private Circulo circulo;
	private Fase fase;
	private Proceso proceso;
	private gov.sir.core.negocio.modelo.Usuario usuarioNegocio;
	private Turno turno;
	private Folio folio;
	private String observacionesAnulacion;
	private String observacionesHabilitar;
	private List<Rol> roles;
	private Ciudadano ciudadanoToEdit;
	
	private Nota nota;
	private gov.sir.core.negocio.modelo.Usuario usuarioCalificador;
	

	/**
	 * @param usuario
	 */
	public EvnTrasladoTurno(Usuario usuario) {
		super(usuario);
	}

	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnTrasladoTurno(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
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
	public Fase getFase() {
		return fase;
	}

	/**
	 * @param fase
	 */
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	/**
	 * @return
	 */
	public Proceso getProceso() {
		return proceso;
	}

	/**
	 * @param proceso
	 */
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNegocio() {
		return usuarioNegocio;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioNegocio(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioNegocio = usuario;
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
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	public String getObservacionesAnulacion() {
		return observacionesAnulacion;
	}

	public void setObservacionesAnulacion(String observacionesAnulacion) {
		this.observacionesAnulacion = observacionesAnulacion;
	}

	public Ciudadano getCiudadanoToEdit() {
		return ciudadanoToEdit;
	}

	public void setCiudadanoToEdit(Ciudadano ciudadanoToEdit) {
		this.ciudadanoToEdit = ciudadanoToEdit;
	}

	public String getObservacionesHabilitar() {
		return observacionesHabilitar;
	}

	public void setObservacionesHabilitar(String observacionesHabilitar) {
		this.observacionesHabilitar = observacionesHabilitar;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public gov.sir.core.negocio.modelo.Usuario getUsuarioCalificador() {
		return usuarioCalificador;
	}

	public void setUsuarioCalificador(gov.sir.core.negocio.modelo.Usuario usuarioCalificador) {
		this.usuarioCalificador = usuarioCalificador;
	}
        
        public List getCanalRecaudoYcuentas(){
        return canalRecaudoYcuentas;
}
    
        public void setCanalRecaudoYcuentas(List canalRecaudoYcuentas){
        this.canalRecaudoYcuentas = canalRecaudoYcuentas;
        }
        
        public List<Rol> getRol(){
            return this.roles;
        }
        
        public void setRoles(List<Rol> roles)
        {
            this.roles = roles;
        }
}
