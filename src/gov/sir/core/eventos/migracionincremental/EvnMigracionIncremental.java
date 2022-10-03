package gov.sir.core.eventos.migracionincremental;

import gov.sir.core.eventos.comun.EvnSIR;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes con respecto a migracion incremental a la capa de negocio.
 * @author gvillal
 */
public class EvnMigracionIncremental extends EvnSIR{
	
	private String matriculaFolio;
	private String numTurno;
	private gov.sir.core.negocio.modelo.Usuario usuarioModelo;
	
	public gov.sir.core.negocio.modelo.Usuario getUsuarioModelo() {
		return usuarioModelo;
	}

	public void setUsuarioModelo(gov.sir.core.negocio.modelo.Usuario usuarioModelo) {
		this.usuarioModelo = usuarioModelo;
	}

	public EvnMigracionIncremental(Usuario usuarioAuriga, gov.sir.core.negocio.modelo.Usuario usuarioMod, String tipoEvento, String matricula, String numTurno) {
		super(usuarioAuriga, tipoEvento);
		this.matriculaFolio = matricula;
		this.numTurno = numTurno;
		this.usuarioModelo = usuarioMod;
	}

	public String getMatriculaFolio() {
		return matriculaFolio;
	}

	public void setMatriculaFolio(String matriculaFolio) {
		this.matriculaFolio = matriculaFolio;
	}

	public String getNumTurno() {
		return numTurno;
	}

	public void setNumTurno(String numTurno) {
		this.numTurno = numTurno;
	}
	
	
	
	/**Esta constante define el tipo evento que para la migracion incremental de un folio */
	public static final String TIPO_MIGRAR_FOLIO = "MIGRAR_FOLIO";
	
	/**Esta constante define el tipo evento que para la migracion incremental de un turno */
	public static final String TIPO_MIGRAR_TURNO = "MIGRAR_TURNO";

}
