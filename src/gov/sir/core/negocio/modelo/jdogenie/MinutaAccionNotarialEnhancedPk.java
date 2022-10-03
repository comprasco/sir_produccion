package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.MinutaAccionNotarialPk;

/**
 * Application identity objectid-class.
 */
public class MinutaAccionNotarialEnhancedPk implements java.io.Serializable {

    public String idAccionNotarial;
    public String idMinuta;

    public MinutaAccionNotarialEnhancedPk() {
    }

    public MinutaAccionNotarialEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idAccionNotarial = s.substring(p, i);
        p = i + 1;
        idMinuta = s.substring(p);
    }
    

	public MinutaAccionNotarialEnhancedPk(MinutaAccionNotarialPk id){
		idMinuta = id.idMinuta;
		idAccionNotarial = id.idAccionNotarial;
	}
            
	public MinutaAccionNotarialPk getMinutaAccionNotarialID(){
		MinutaAccionNotarialPk id = new MinutaAccionNotarialPk();
		id.idMinuta = idMinuta;
		id.idAccionNotarial = idAccionNotarial;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinutaAccionNotarialEnhancedPk)) return false;

        final MinutaAccionNotarialEnhancedPk id = (MinutaAccionNotarialEnhancedPk) o;

        if (this.idAccionNotarial != null ? !idAccionNotarial.equals(id.idAccionNotarial) : id.idAccionNotarial != null) return false;
        if (this.idMinuta != null ? !idMinuta.equals(id.idMinuta) : id.idMinuta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAccionNotarial != null ? idAccionNotarial.hashCode() : 0);
        result = 29 * result + (idMinuta != null ? idMinuta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAccionNotarial);
        buffer.append('-');
        buffer.append(idMinuta);
        return buffer.toString();
    }
}