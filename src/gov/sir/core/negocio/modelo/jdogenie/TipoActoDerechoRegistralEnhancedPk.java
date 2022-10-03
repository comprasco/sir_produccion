package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoActoDerechoRegistralPk;

/**
 * Application identity objectid-class.
 */
public class TipoActoDerechoRegistralEnhancedPk implements java.io.Serializable {

    public String idTipoActo;
    public String idTipoDerechoReg;

    public TipoActoDerechoRegistralEnhancedPk() {
    }
    
	public TipoActoDerechoRegistralEnhancedPk(TipoActoDerechoRegistralPk id){
		idTipoDerechoReg = id.idTipoDerechoReg;
		idTipoActo       = id.idTipoActo;        	
	}
    
	public TipoActoDerechoRegistralPk getTipoActoDerechoRegistralID(){
		TipoActoDerechoRegistralPk id = new TipoActoDerechoRegistralPk();
		id.idTipoDerechoReg = idTipoDerechoReg;
		id.idTipoActo       = idTipoActo;
		return id;
	}                

    public TipoActoDerechoRegistralEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idTipoActo = s.substring(p, i);
        p = i + 1;
        idTipoDerechoReg = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoActoDerechoRegistralEnhancedPk)) return false;

        final TipoActoDerechoRegistralEnhancedPk id = (TipoActoDerechoRegistralEnhancedPk) o;

        if (this.idTipoActo != null ? !idTipoActo.equals(id.idTipoActo) : id.idTipoActo != null) return false;
        if (this.idTipoDerechoReg != null ? !idTipoDerechoReg.equals(id.idTipoDerechoReg) : id.idTipoDerechoReg != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoActo != null ? idTipoActo.hashCode() : 0);
        result = 29 * result + (idTipoDerechoReg != null ? idTipoDerechoReg.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoActo);
        buffer.append('-');
        buffer.append(idTipoDerechoReg);
        return buffer.toString();
    }
}