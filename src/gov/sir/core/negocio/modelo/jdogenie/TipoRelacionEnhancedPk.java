package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoRelacionPk;

/**
 * Application identity objectid-class.
 */
public class TipoRelacionEnhancedPk implements java.io.Serializable {

    public String idTipoRelacion;

    public TipoRelacionEnhancedPk() {
    }

    public TipoRelacionEnhancedPk(String s) {
        int i, p = 0;
        idTipoRelacion = s.substring(p);
    }
    

	public TipoRelacionEnhancedPk (TipoRelacionPk id){
		idTipoRelacion = id.idTipoRelacion;
	}


	public TipoRelacionPk getTipoRelacionID()
	{
		TipoRelacionPk id = new TipoRelacionPk ();
		id.idTipoRelacion = idTipoRelacion;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoRelacionEnhancedPk)) return false;

        final TipoRelacionEnhancedPk id = (TipoRelacionEnhancedPk) o;

        if (this.idTipoRelacion != null ? !idTipoRelacion.equals(id.idTipoRelacion) : id.idTipoRelacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoRelacion != null ? idTipoRelacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoRelacion);
        return buffer.toString();
    }
}