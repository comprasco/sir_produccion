

package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;


/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LiquidacionTurnoConsultaEnhanced extends LiquidacionEnhanced {

	private AlcanceGeograficoEnhanced alcanceGeografico;
	
	
	public LiquidacionTurnoConsultaEnhanced(){
	}

	/**
	 * @return
	 */
	public AlcanceGeograficoEnhanced getAlcanceGeografico() {
		return alcanceGeografico;
	}

	/**
	 * @param geografico
	 */
	public void setAlcanceGeografico(AlcanceGeograficoEnhanced geografico) {
		alcanceGeografico = geografico;
	}
	
	public static LiquidacionTurnoConsultaEnhanced enhance(LiquidacionTurnoConsulta liquidacion){
		return (LiquidacionTurnoConsultaEnhanced) Enhanced.enhance(liquidacion);
	}
}