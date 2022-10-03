package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a SolicitudVinculada
 */
public class SolicitudVinculadaPk implements java.io.Serializable {

    public String idSolicitud;
    public String idSolicitud1;

    public SolicitudVinculadaPk() {
    }

    public SolicitudVinculadaPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        idSolicitud1 = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudVinculadaPk)) return false;

        final SolicitudVinculadaPk id = (SolicitudVinculadaPk) o;

        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idSolicitud1 != null ? !idSolicitud1.equals(id.idSolicitud1) : id.idSolicitud1 != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idSolicitud1 != null ? idSolicitud1.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idSolicitud1);
        return buffer.toString();
    }
}