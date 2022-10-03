package gov.sir.core.negocio.modelo;

/** Clase que definelos atributos que identifican a SolicitudPermisoCorreccion   */

public class SolicitudPermisoCorreccionPk implements java.io.Serializable {

    public String idPermiso;
    public String idSolicitud;

    public SolicitudPermisoCorreccionPk() {
    }

    public SolicitudPermisoCorreccionPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idPermiso = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudPermisoCorreccionPk)) return false;

        final SolicitudPermisoCorreccionPk id = (SolicitudPermisoCorreccionPk) o;

        if (this.idPermiso != null ? !idPermiso.equals(id.idPermiso) : id.idPermiso != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idPermiso != null ? idPermiso.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idPermiso);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}