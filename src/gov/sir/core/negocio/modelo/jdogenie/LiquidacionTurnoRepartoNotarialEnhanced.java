package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial;


/**
 * @author dlopez
 * Liquidación turno reparto notarial
 */
public class LiquidacionTurnoRepartoNotarialEnhanced extends LiquidacionEnhanced {

		
	public LiquidacionTurnoRepartoNotarialEnhanced(){
	}

	public static LiquidacionTurnoRepartoNotarialEnhanced enhance(LiquidacionTurnoRepartoNotarial liquidacion){
		return (LiquidacionTurnoRepartoNotarialEnhanced) Enhanced.enhance(liquidacion);
	}
}
