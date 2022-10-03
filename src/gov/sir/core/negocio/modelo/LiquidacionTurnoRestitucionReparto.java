package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * @author dlopez
 * Liquidaci�n turno para el proceso de restituci�n 
 * de un reparto notarial. 
 */
public class LiquidacionTurnoRestitucionReparto extends Liquidacion implements TransferObject{

	public static final String CODE_LIQUIDACION = "RESTITUCION";
        private static final long serialVersionUID = 1L;
	/** Metodo constructor  */
	public LiquidacionTurnoRestitucionReparto(){
	}


}