package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoConsultaPk;

/**
 * Application identity objectid-class.
 */
public class TipoConsultaEnhancedPk implements java.io.Serializable {

    public String idTipoConsulta;

    public TipoConsultaEnhancedPk() {
    }
    
    public TipoConsultaEnhancedPk (TipoConsultaPk id)
    {
    	idTipoConsulta = id.idTipoConsulta;
    }

    public TipoConsultaEnhancedPk(String s) {
        int i, p = 0;
        idTipoConsulta = s.substring(p);
    }
    
    public TipoConsultaPk getTipoConsultaID()
    {
    	TipoConsultaPk id = new TipoConsultaPk();
    	id.idTipoConsulta = idTipoConsulta;
    	return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoConsultaEnhancedPk)) return false;

        final TipoConsultaEnhancedPk id = (TipoConsultaEnhancedPk) o;

        if (this.idTipoConsulta != null ? !idTipoConsulta.equals(id.idTipoConsulta) : id.idTipoConsulta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoConsulta != null ? idTipoConsulta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoConsulta);
        return buffer.toString();
    }
}