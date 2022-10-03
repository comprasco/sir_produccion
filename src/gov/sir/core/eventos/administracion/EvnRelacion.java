package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

public class EvnRelacion extends EvnSIR {

	public static final String SELECCIONAR_RELACION = "SELECCIONAR_RELACION";
	public static final String SELECCIONAR_FASE = "SELECCIONAR_FASE";
	public static final String SELECCIONAR_PROCESO = "SELECCIONAR_PROCESO";
	public static final String CARGAR_DATOS = "CARGAR_DATOS";
	public static final String CREAR_RELACION = "CREAR_RELACION";
	public static final String IMPRIMIR = "IMPRIMIR";
        public static final String IMPRIMIR_REPARTO = "IMPRIMIR_REPARTO";
	public static final String INGRESAR_RELACION = "INGRESAR_RELACION";
	
	public static final String VER_DETALLE_RELACION = "VER_DETALLE_RELACION";

	private long idProceso;
	private String idTipoRelacion;
	private String idFase;
	private String idRelacion;
    private String idReparto;
	private String nota;
	private Circulo circulo;
	private String[] turnosRelacion;
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private Estacion estacion;
	private Rol rol;
	private boolean validarPrimaCorrecionCalificacion;
	private String ImpresoraSeleccionada;
	/**Se dice que el avance de turnos es automático cuando a partir del número de relación se avanzan los 
	 * turnos y no los que el usuario ha seleccionado.*/
	private boolean avanceTurnosAutomatico = false;

	public EvnRelacion(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}

	public long getIdProceso() {
		return idProceso;
	}

        public String getImpresoraSeleccionada() {
            return ImpresoraSeleccionada;
        }

        public void setImpresoraSeleccionada(String ImpresoraSeleccionada) {
            this.ImpresoraSeleccionada = ImpresoraSeleccionada;
        }
        
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}

	public String getIdFase() {
		return idFase;
	}

	public void setIdTipoRelacion(String idTipoRelacion) {
		this.idTipoRelacion = idTipoRelacion;
	}

        public void setIdReparto(String idReparto) {
          this.idReparto = idReparto;
        }

	public String getIdTipoRelacion() {
		return idTipoRelacion;
	}

        public String getIdReparto() {
                return idReparto;
        }

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public String[] getTurnosRelacion() {
		return turnosRelacion;
	}

	public void setTurnosRelacion(String[] turnosRelacion) {
		this.turnosRelacion = turnosRelacion;
	}

	public void setIdRelacion(String idRelacion) {
		this.idRelacion = idRelacion;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getIdRelacion() {
		return idRelacion;
	}

	public String getNota() {
		return nota;
	}

	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		this.usuarioSIR = usuarioSIR;
	}

	public boolean isAvanceTurnosAutomatico() {
		return avanceTurnosAutomatico;
	}

	public void setAvanceTurnosAutomatico(boolean avanceTurnosAutomatico) {
		this.avanceTurnosAutomatico = avanceTurnosAutomatico;
	}

	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isValidarPrimaCorrecionCalificacion() {
		return validarPrimaCorrecionCalificacion;
	}

	public void setValidarPrimaCorrecionCalificacion(boolean validarPrimaCorrecionCalificacion) {
		this.validarPrimaCorrecionCalificacion = validarPrimaCorrecionCalificacion;
	}
}
