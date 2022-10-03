package gov.sir.core.eventos.administracion;

import java.util.Date;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Minuta;

/**
 * @author mmunoz
 */
public class EvnConsultasReparto extends EvnSIR {

	public static final String MINUTA_RADICACION = "MINUTA_RADICACION";

	public static final String LISTAR_POR_FECHAS = "LISTAR_POR_FECHAS";
	
	public static final String LISTAR_POR_OTORGANTE = "LISTAR_POR_OTORGANTE";
	
	public static final String LISTAR_PENDIENTES = "LISTAR_PENDIENTES";
	
	public static final String ANULAR_MINUTA = "ANULAR_MINUTA";
	
	public static final String ENVIAR_MINUTA_EDICION = "ENVIAR_MINUTA_EDICION";
	
	public static final String IMPRIMIR_SOLICITUD_MINUTA="IMPRIMIR_SOLICITUD_MINUTA";
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private String idTurno;
	
	private String otorgante;
	
	private Minuta minuta;
	
	private Minuta oldMinuta;
	
	private boolean esPersonaNatural;
	
	private Circulo circulo;

	private long estadoReparto;
	
	private String notaInformativa;
	
	private String UID;

	
	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnConsultasReparto(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	/**
	 * @return
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @return
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechas(Date fechaInicio, Date fechaFin){
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	/**
	 * @return
	 */
	public String getIdTurno() {
		return idTurno;
	}

	/**
	 * @param string
	 */
	public void setIdTurno(String string) {
		idTurno = string;
	}

	/**
	 * @return
	 */
	public String getOtorgante() {
		return otorgante;
	}

	/**
	 * @param string
	 */
	public void setOtorgante(String string, boolean esPersonaNatural) {
		otorgante = string;
		this.esPersonaNatural = esPersonaNatural;
	}

	/**
	 * @return
	 */
	public Minuta getMinuta() {
		return minuta;
	}

	/**
	 * @param string
	 */
	public void setMinuta(Minuta string) {
		minuta = string;
	}

	/**
	 * @return
	 */
	public boolean isEsPersonaNatural() {
		return esPersonaNatural;
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
	public long getEstadoReparto() {
		return estadoReparto;
	}

	/**
	 * @param l
	 */
	public void setEstadoReparto(long l) {
		estadoReparto = l;
	}

	/**
	 * @return Returns the notaInformativa.
	 */
	public String getNotaInformativa() {
		return notaInformativa;
	}
	/**
	 * @param notaInformativa The notaInformativa to set.
	 */
	public void setNotaInformativa(String notaInformativa) {
		this.notaInformativa = notaInformativa;
	}
	/**
	 * @return Returns the oldMinuta.
	 */
	public Minuta getOldMinuta() {
		return oldMinuta;
	}
	/**
	 * @param oldMinuta The oldMinuta to set.
	 */
	public void setOldMinuta(Minuta oldMinuta) {
		this.oldMinuta = oldMinuta;
	}
	
	/**
	 * @return Returns the uID.
	 */
	public String getUID() {
		return UID;
	}
	/**
	 * @param uid The uID to set.
	 */
	public void setUID(String uid) {
		UID = uid;
	}
}
