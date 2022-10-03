package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ActoresRepartoNotarialPk;



/**
 * Application identity objectid-class.
 */
public class ActoresRepartoNotarialEnhancedPk implements java.io.Serializable {

    public String idCirculo;

    public ActoresRepartoNotarialEnhancedPk() {
    }
    
    public ActoresRepartoNotarialEnhancedPk (ActoresRepartoNotarialPk id)
    {
    	idCirculo = id.idCirculo;
    }

    public ActoresRepartoNotarialEnhancedPk(String s) {
        int i, p = 0;
        idCirculo = s.substring(p);
    }
    
    public ActoresRepartoNotarialPk getActoresRepartoNotarialID()
    {
    	ActoresRepartoNotarialPk id = new ActoresRepartoNotarialPk ();
		id.idCirculo = idCirculo;
		return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActoresRepartoNotarialEnhancedPk)) return false;

        final ActoresRepartoNotarialEnhancedPk id = (ActoresRepartoNotarialEnhancedPk) o;

        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);
        return buffer.toString();
    }
}