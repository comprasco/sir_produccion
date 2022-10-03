package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.PermisoCorreccionPk;

/**
 * Application identity objectid-class.
 */
public class PermisoCorreccionEnhancedPk implements java.io.Serializable {

    public String idPermiso;

    public PermisoCorreccionEnhancedPk() {
    }

    public PermisoCorreccionEnhancedPk(String s) {
        int i, p = 0;
        idPermiso = s.substring(p);
    }
    

	public PermisoCorreccionEnhancedPk(PermisoCorreccionPk id) {
		idPermiso = id.idPermiso;
	}


	public PermisoCorreccionPk getPermisoCorreccionID() {
		PermisoCorreccionPk id = new PermisoCorreccionPk();
		id.idPermiso = idPermiso;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermisoCorreccionEnhancedPk)) return false;

        final PermisoCorreccionEnhancedPk id = (PermisoCorreccionEnhancedPk) o;

        if (this.idPermiso != null ? !idPermiso.equals(id.idPermiso) : id.idPermiso != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idPermiso != null ? idPermiso.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idPermiso);
        return buffer.toString();
    }
}