package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoCalculoPk;

/**
 * Application identity objectid-class.
 */
public class TipoCalculoEnhancedPk implements java.io.Serializable {

    public String idTipoCalculo;

    public TipoCalculoEnhancedPk() {
    }

    public TipoCalculoEnhancedPk(String s) {
        int i, p = 0;
        idTipoCalculo = s.substring(p);
    }
    
	public TipoCalculoEnhancedPk(TipoCalculoPk id){
		idTipoCalculo = id.idTipoCalculo;        	
	}
    
	public TipoCalculoPk getTipoCalculoID(){
		TipoCalculoPk id = new TipoCalculoPk();
		id.idTipoCalculo  = idTipoCalculo;
		return id;
	}              

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCalculoEnhancedPk)) return false;

        final TipoCalculoEnhancedPk id = (TipoCalculoEnhancedPk) o;

        if (this.idTipoCalculo != null ? !idTipoCalculo.equals(id.idTipoCalculo) : id.idTipoCalculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoCalculo != null ? idTipoCalculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoCalculo);
        return buffer.toString();
    }
}