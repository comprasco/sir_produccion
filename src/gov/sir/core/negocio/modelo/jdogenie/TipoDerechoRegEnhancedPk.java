package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoDerechoRegPk;

/**
 * Application identity objectid-class.
 */
public class TipoDerechoRegEnhancedPk implements java.io.Serializable {

    public String idTipoDerechoReg;

    public TipoDerechoRegEnhancedPk() {
    }
    
    public TipoDerechoRegEnhancedPk (TipoDerechoRegPk id )
    {
    	idTipoDerechoReg = id.idTipoDerechoReg;
    }
    

    public TipoDerechoRegEnhancedPk(String s) {
        int i, p = 0;
        idTipoDerechoReg = s.substring(p);
    }
    
    public TipoDerechoRegPk getTipoDerechoRegID()
    {
		TipoDerechoRegPk id = new TipoDerechoRegPk();
		id.idTipoDerechoReg = idTipoDerechoReg;
		return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoDerechoRegEnhancedPk)) return false;

        final TipoDerechoRegEnhancedPk id = (TipoDerechoRegEnhancedPk) o;

        if (this.idTipoDerechoReg != null ? !idTipoDerechoReg.equals(id.idTipoDerechoReg) : id.idTipoDerechoReg != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoDerechoReg != null ? idTipoDerechoReg.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoDerechoReg);
        return buffer.toString();
    }
}