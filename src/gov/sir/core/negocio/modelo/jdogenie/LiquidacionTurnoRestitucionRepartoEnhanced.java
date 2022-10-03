package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto;


/**
 * @author dlopez
 * Liquidaci�n turno restituci�n reparto notarial.
 */
public class LiquidacionTurnoRestitucionRepartoEnhanced extends LiquidacionEnhanced {

		
	public LiquidacionTurnoRestitucionRepartoEnhanced(){
	}

	public static LiquidacionTurnoRestitucionRepartoEnhanced enhance(LiquidacionTurnoRestitucionReparto liquidacion){
		return (LiquidacionTurnoRestitucionRepartoEnhanced) Enhanced.enhance(liquidacion);
	}
}
