package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.RepartoNotarialPk;

/**
 * Application identity objectid-class.
 */
public class RepartoNotarialEnhancedPk implements java.io.Serializable {

    public String idRepartoNotarial;

    public RepartoNotarialEnhancedPk() {
    }

    public RepartoNotarialEnhancedPk(String s) {
        int i, p = 0;
        idRepartoNotarial = s.substring(p);
    }
    

	public RepartoNotarialEnhancedPk(RepartoNotarialPk id){
	    idRepartoNotarial = id.idRepartoNotarial;
	}
    
	public RepartoNotarialPk getRepartoNotarialID(){
		RepartoNotarialPk rta = new RepartoNotarialPk();
		rta.idRepartoNotarial = idRepartoNotarial;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepartoNotarialEnhancedPk)) return false;

        final RepartoNotarialEnhancedPk id = (RepartoNotarialEnhancedPk) o;

        if (this.idRepartoNotarial != null ? !idRepartoNotarial.equals(id.idRepartoNotarial) : id.idRepartoNotarial != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idRepartoNotarial != null ? idRepartoNotarial.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idRepartoNotarial);
        return buffer.toString();
    }
}