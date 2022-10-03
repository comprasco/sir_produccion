package gov.sir.core.negocio.modelo.imprimibles.util;

import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;

import java.util.List;

/**
 * 
 * @author Diego Alejandro Hernandez Uribe
 * 
 */
public class Rupta {

	private final static String CODIGO_RUPTA = "474";
	public final static String SIGLA = "RUPTA";
	
	public static boolean liquidacionEsRupta(final Liquidacion liquidacion){  
		if(liquidacion instanceof LiquidacionTurnoRegistro){
			final List actos = ((LiquidacionTurnoRegistro)liquidacion).getActos();    
		    for (int contActos=0; contActos<actos.size(); contActos++){
			    if (((Acto)actos.get(contActos)).getTipoActo().getIdTipoActo().equals(Rupta.CODIGO_RUPTA)){
			    	return true;
			    }
		    }
		}
		return false;
	}
}
