package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TarifaRegistralPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhancedPk;

/**
* @Autor: Santiago Vásquez
* @Change: 2062.TARIFAS.REGISTRALES.2017
*/
public class TarifaRegistralEnhancedPk implements java.io.Serializable {
    
    public String idTarifa;

    public TarifaRegistralEnhancedPk() {
    }
    
    public TarifaRegistralEnhancedPk(TarifaRegistralPk id){
            idTarifa = id.idTarifa;        	
    }
    
    public TarifaRegistralPk getTipoActoDerechoRegistralID(){
            TarifaRegistralPk id = new TarifaRegistralPk();
            id.idTarifa = idTarifa;
            return id;
    }            

    public TarifaRegistralEnhancedPk(String s) {
        int i, p = 0;
        idTarifa = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoActoEnhancedPk)) return false;

        final TipoActoEnhancedPk id = (TipoActoEnhancedPk) o;

        if (this.idTarifa != null ? !idTarifa.equals(id.idTipoActo) : id.idTipoActo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTarifa != null ? idTarifa.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTarifa);
        return buffer.toString();
    }
}
