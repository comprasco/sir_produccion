package gov.sir.core.eventos.migracionincremental;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Turno;

public class EvnRespMigracionIncremental extends EvnSIRRespuesta {

	
	private String matricula;
	private Turno turno;	
	/**
	 * Constructor del evento de respuesta con parametros.
	 * @param folio Folio
	 */
	public EvnRespMigracionIncremental(String matricula) {
		super(matricula,FOLIO_EXISTENTE);
		this.matricula = matricula;
	}
	
	/**
	 * Constructor del evento de respuesta con parametros.
	 * Este evento se retorna cuando se relaciona un folio con el turno. 
	 * @param folio Folio
	 */
	public EvnRespMigracionIncremental(Turno turno) {
		super(turno,TURNO_EXISTENTE);
		this.turno = turno;
	}
	
	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String FOLIO_EXISTENTE = "FOLIO_EXISTENTE";
	
	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TURNO_EXISTENTE = "TURNO_EXISTENTE";

}
