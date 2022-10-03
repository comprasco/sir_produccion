package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * @author dsalas
 * Liquidación turno fotocopia
 */
public class LiquidacionTurnoFotocopia extends Liquidacion implements TransferObject{
    
    /** @author : HGOMEZ
    *** @change : Se declara la variable tarifa para asignar valor igual a cero 
    *** cuando una fotocopia es exenta al igual que los métodos para asignar y 
    *** obtener el valor de la misma.
    *** Caso Mantis : 12288
    */
    private String tarifa;
    private static final long serialVersionUID = 1L;
    public String getTarifa() {
        return tarifa;
    }
    
    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }
    
        /** Metodo constructor */
	public LiquidacionTurnoFotocopia(){
	}


}
