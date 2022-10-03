package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoActoPk;

/**
 * Application identity objectid-class.
 */
public class TipoActoEnhancedPk implements java.io.Serializable {

    public String idTipoActo;

    public TipoActoEnhancedPk() {
    }
    
	public TipoActoEnhancedPk(TipoActoPk id){
		idTipoActo = id.idTipoActo;        	
	}
    
	public TipoActoPk getTipoActoDerechoRegistralID(){
		TipoActoPk id = new TipoActoPk();
		id.idTipoActo  = idTipoActo;
		return id;
	}            

    public TipoActoEnhancedPk(String s) {
        int i, p = 0;
        idTipoActo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoActoEnhancedPk)) return false;

        final TipoActoEnhancedPk id = (TipoActoEnhancedPk) o;

        if (this.idTipoActo != null ? !idTipoActo.equals(id.idTipoActo) : id.idTipoActo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoActo != null ? idTipoActo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoActo);
        return buffer.toString();
    }
}