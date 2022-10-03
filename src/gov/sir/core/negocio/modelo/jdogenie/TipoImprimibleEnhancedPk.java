package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoImprimiblePk;

/**
 * Application identity objectid-class.
 */
public class TipoImprimibleEnhancedPk implements java.io.Serializable {

    public String idTipoImprimible;

    public TipoImprimibleEnhancedPk() {
    }

    public TipoImprimibleEnhancedPk(String s) {
        int i, p = 0;
        idTipoImprimible = s.substring(p);
    }
    
	public TipoImprimibleEnhancedPk (TipoImprimiblePk id)
	{
		idTipoImprimible = id.idTipoImprimible;
	}

	public TipoImprimiblePk getTipoImprimibleID()
	{
		TipoImprimiblePk id = new TipoImprimiblePk();
		id.idTipoImprimible = idTipoImprimible;
		return id;
	}


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoImprimibleEnhancedPk)) return false;

        final TipoImprimibleEnhancedPk id = (TipoImprimibleEnhancedPk) o;

        if (this.idTipoImprimible != null ? !idTipoImprimible.equals(id.idTipoImprimible) : id.idTipoImprimible != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoImprimible != null ? idTipoImprimible.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoImprimible);
        return buffer.toString();
    }
}