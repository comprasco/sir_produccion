package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;

/**
 * @author dsalas
 * Liquidación turno fotocopia
 */
public class LiquidacionTurnoFotocopiaEnhanced extends LiquidacionEnhanced{
    
    /** @author : HGOMEZ
    *** @change : Se declara la variable tarifa para asignar valor igual a cero 
    *** cuando una fotocopia es exenta al igual que los métodos para asignar y 
    *** obtener el valor de la misma.
    *** Caso Mantis : 12288
    */
    private String tarifa;

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }
		
	public LiquidacionTurnoFotocopiaEnhanced(){
	}
	
	public static LiquidacionTurnoFotocopiaEnhanced enhance(LiquidacionTurnoFotocopia liquidacion){
		return (LiquidacionTurnoFotocopiaEnhanced) Enhanced.enhance(liquidacion);
	}

}
