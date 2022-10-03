package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a PermisoCorreccion	  */

public class PermisoCorreccionPk implements java.io.Serializable {

    public String idPermiso;

    public PermisoCorreccionPk() {
    }

    public PermisoCorreccionPk(String s) {
        int i, p = 0;
        idPermiso = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermisoCorreccionPk)) return false;

        final PermisoCorreccionPk id = (PermisoCorreccionPk) o;

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