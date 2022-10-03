package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.AccionNotarialPk;

/**
 * Clase que define los atributos que identifican a AccionNotarialEnhanced
 */
public class AccionNotarialEnhancedPk implements java.io.Serializable {

    public String idAccionNotarial;

    public AccionNotarialEnhancedPk() {
    }
    
    public AccionNotarialEnhancedPk(AccionNotarialPk id){
    	idAccionNotarial = id.idAccionNotarial;
    }
    
    public AccionNotarialPk getAccionNotarialID(){
		AccionNotarialPk rta = new AccionNotarialPk();
		rta.idAccionNotarial = idAccionNotarial;
		return rta;
    }

    public AccionNotarialEnhancedPk(String s) {
        int i, p = 0;
        idAccionNotarial = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccionNotarialEnhancedPk)) return false;

        final AccionNotarialEnhancedPk id = (AccionNotarialEnhancedPk) o;

        if (this.idAccionNotarial != null ? !idAccionNotarial.equals(id.idAccionNotarial) : id.idAccionNotarial != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAccionNotarial != null ? idAccionNotarial.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAccionNotarial);
        return buffer.toString();
    }
}