package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoOficioPk;

/**
 * Application identity objectid-class.
 */
public class TipoOficioEnhancedPk implements java.io.Serializable {

    public String idTipoOficio;

    public TipoOficioEnhancedPk() {
    }

    public TipoOficioEnhancedPk(String s) {
        int i, p = 0;
        idTipoOficio = s.substring(p);
    }
    

	public TipoOficioEnhancedPk (TipoOficioPk id)
	{
		idTipoOficio = id.idTipoOficio;
	}


	public TipoOficioPk getTipoOficioID()
	{
		TipoOficioPk id = new TipoOficioPk();
		id.idTipoOficio = idTipoOficio;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoOficioEnhancedPk)) return false;

        final TipoOficioEnhancedPk id = (TipoOficioEnhancedPk) o;

        if (this.idTipoOficio != null ? !idTipoOficio.equals(id.idTipoOficio) : id.idTipoOficio != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoOficio != null ? idTipoOficio.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoOficio);
        return buffer.toString();
    }
}