package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ImprimiblePk;

/**
 * Application identity objectid-class.
 */
public class ImprimibleEnhancedPk implements java.io.Serializable {

    public int idImprimible;

    public ImprimibleEnhancedPk() {
    }
    
	public ImprimibleEnhancedPk(ImprimiblePk id){
		idImprimible = id.idImprimible;
	}
	
	public ImprimiblePk getImprimibleID(){
		ImprimiblePk id = new ImprimiblePk();
		id.idImprimible = idImprimible;
		return id;
	}

    public ImprimibleEnhancedPk(String s) {
        int i, p = 0;
        idImprimible = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImprimibleEnhancedPk)) return false;

        final ImprimibleEnhancedPk id = (ImprimibleEnhancedPk) o;

        if (this.idImprimible != id.idImprimible) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idImprimible ^ (idImprimible >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idImprimible);
        return buffer.toString();
    }
}