package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.OPLookupTypes;

import org.auriga.core.modelo.transferObjects.Usuario;

public class EvnContrasena extends EvnSIR{
	
	/**Esta constante se utiliza  para identificar el evento de Consulta de objetos oplookup code  */
	public static final String LISTAR_OPLOOKUP_CODES_MESA_AYUDA = "LISTAR_OPLOOKUP_CODES_MESA_AYUDA";
	
	private OPLookupTypes opLookupType;
	/**
	 * @param usuario
	 */
	public EvnContrasena(Usuario usuario) {
		super(usuario);
	}

	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnContrasena(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}
	
	/**
	 * @return
	 */
	public OPLookupTypes getOpLookupType() {
		return opLookupType;
	}

	/**
	 * @param types
	 */
	public void setOpLookupType(OPLookupTypes types) {
		opLookupType = types;
	}

}
