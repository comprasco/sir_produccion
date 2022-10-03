package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoTarifaPk;

/**
 * Application identity objectid-class.
 */
public class TipoTarifaEnhancedPk implements java.io.Serializable {

    public String idTipo;

    public TipoTarifaEnhancedPk() {
    }
    
    public TipoTarifaEnhancedPk (TipoTarifaPk id)
    {
    	idTipo = id.idTipo;
    }

    public TipoTarifaEnhancedPk(String s) {
        int i, p = 0;
        idTipo = s.substring(p);
    }
    
    public TipoTarifaPk getTipoTarifaID()
    {
		TipoTarifaPk id = new TipoTarifaPk ();
		id.idTipo = idTipo;
		return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoTarifaEnhancedPk)) return false;

        final TipoTarifaEnhancedPk id = (TipoTarifaEnhancedPk) o;

        if (this.idTipo != null ? !idTipo.equals(id.idTipo) : id.idTipo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipo != null ? idTipo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipo);
        return buffer.toString();
    }
}