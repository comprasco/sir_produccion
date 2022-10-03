package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoImpuestoPk;

/**
 * Application identity objectid-class.
 */
public class TipoImpuestoEnhancedPk implements java.io.Serializable {

    public String idTipoImpuesto;

    public TipoImpuestoEnhancedPk() {
    }
    

    public TipoImpuestoEnhancedPk (TipoImpuestoPk id)
    {
    	idTipoImpuesto = id.idTipoImpuesto;
    }

    public TipoImpuestoEnhancedPk(String s) {
        int i, p = 0;
        idTipoImpuesto = s.substring(p);
    }
    
    public TipoImpuestoPk getTipoImpuestoID()
    {
		TipoImpuestoPk id = new TipoImpuestoPk ();
		id.idTipoImpuesto = idTipoImpuesto;
		return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoImpuestoEnhancedPk)) return false;

        final TipoImpuestoEnhancedPk id = (TipoImpuestoEnhancedPk) o;

        if (this.idTipoImpuesto != null ? !idTipoImpuesto.equals(id.idTipoImpuesto) : id.idTipoImpuesto != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoImpuesto != null ? idTipoImpuesto.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoImpuesto);
        return buffer.toString();
    }
}