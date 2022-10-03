package gov.sir.core.eventos.registro;


import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author mmunoz
 */
public class EvnConsultaFolio extends EvnSIR {
	
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";
	
	private Folio folio;

	private Turno turno;

	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
	/**
	 * @param usuarioAuriga
	 * @param folio
	 */
	public EvnConsultaFolio(Usuario usuarioAuriga, Folio folio) {
		super(usuarioAuriga, EvnConsultaFolio.CONSULTAR_FOLIO);
		this.folio = folio;
	}
	
	

	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}



	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno=turno;
		
	}

	/**
	 * @return Returns the turno.
	 */
	public Turno getTurno() {
		return turno;
	}



	/**
	 * @param usuarioNeg
	 */
	public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		this.usuarioNeg = usuarioNeg;
		
	}
	/**
	 * @return Returns the usuarioNeg.
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}
}
