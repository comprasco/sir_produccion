package gov.sir.core.eventos.comun;

import java.util.Hashtable;

import gov.sir.core.negocio.modelo.Turno;

/**
 * @author mmunoz
 */
public class EvnRespNotas extends EvnSIRRespuesta{

	private Turno turno;
	private boolean impreso = false;
	private int idImprimible = 0;
	private Hashtable validacionAnotacionesTemporales;
	public static final String TURNO_NOTA_ADICIONADA = "TURNO_NOTA_ADICIONADA";
	
	public static final String AGREGAR_NOTA_DEVOLUTIVA = "AGREGAR_NOTA_DEVOLUTIVA";
	
	public static final String GUARDAR_NOTAS_DEVOLUTIVAS = "GUARDAR_NOTAS_DEVOLUTIVAS";
	
	public static final String REGISTRAR_CALIFICACION_PARCIAL = "REGISTRAR_CALIFICACION_PARCIAL";
	
	public static final String ELIMINAR_NOTAS_INFORMATIVAS_ULTIMA_FASE_TURNO = 
		"ELIMINAR_NOTAS_INFORMATIVAS_ULTIMA_FASE_TURNO";

	/**
	 * @param payload
	 */
	public EvnRespNotas(Turno turno) {
		super(turno,TURNO_NOTA_ADICIONADA);
		this.turno = turno;
	}
	
	/**
	 * @param payload
	 */
	public EvnRespNotas(Turno turno, String tipoEvento) {
		super(turno,tipoEvento );
		this.turno = turno;
	}	
	
	/**
	 * @param payload
	 */
	public EvnRespNotas(Turno turno, String tipoEvento, boolean impreso) {
		super(turno,tipoEvento );
		this.turno = turno;
		this.impreso = impreso;
	}		
	
	/**
	 * @param payload
	 */
	public EvnRespNotas(Turno turno, String tipoEvento, boolean impreso, int idImprimible) {
		super(turno,tipoEvento );
		this.turno = turno;
		this.impreso = impreso;
		this.idImprimible = idImprimible;
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
	public boolean isImpreso() {
		return impreso;
	}

	/**
	 * @return
	 */
	public int getIdImprimible() {
		return idImprimible;
	}
	
	/**
	 * @return
	 */
	public Hashtable getValidacionAnotacionesTemporales() {
		return validacionAnotacionesTemporales;
	}

	/**
	 * @param hashtable
	 */
	public void setValidacionAnotacionesTemporales(Hashtable hashtable) {
		validacionAnotacionesTemporales = hashtable;
	}

}
