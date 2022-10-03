package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class NotaActuacionPk implements java.io.Serializable {

    public String idNotaActuacion;
    public String idSolicitud;

    public NotaActuacionPk() {
    }

    public NotaActuacionPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idNotaActuacion = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaActuacionPk)) return false;

        final NotaActuacionPk id = (NotaActuacionPk) o;

        if (this.idNotaActuacion != null ? !idNotaActuacion.equals(id.idNotaActuacion) : id.idNotaActuacion != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idNotaActuacion != null ? idNotaActuacion.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idNotaActuacion);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}