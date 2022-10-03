package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ImpresionPk;

/**
 * Application identity objectid-class.
 */
public class ImpresionEnhancedPk implements java.io.Serializable {

    public String idSesion;

    public ImpresionEnhancedPk() {
    }

    public ImpresionEnhancedPk(String s) {
        int i, p = 0;
        idSesion = s.substring(p);
    }
    
	public ImpresionEnhancedPk(ImpresionPk id){
		idSesion = id.idSesion;
	}
	
	public ImpresionPk getImpresionD(){
		ImpresionPk id = new ImpresionPk();
		id.idSesion = idSesion;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImpresionEnhancedPk)) return false;

        final ImpresionEnhancedPk id = (ImpresionEnhancedPk) o;

        if (this.idSesion != null ? !idSesion.equals(id.idSesion) : id.idSesion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSesion != null ? idSesion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSesion);
        return buffer.toString();
    }
}