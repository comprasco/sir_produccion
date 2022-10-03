package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Solicitud  */

public class SolicitudPk implements java.io.Serializable {
    public String idSolicitud;

    public SolicitudPk() {
    }

    public SolicitudPk(String s) {
        int i;
        int p = 0;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SolicitudPk)) {
            return false;
        }

        final SolicitudPk id = (SolicitudPk) o;

        if ((this.idSolicitud != null)
                ? (!idSolicitud.equals(id.idSolicitud))
                    : (id.idSolicitud != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idSolicitud != null) ? idSolicitud.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);

        return buffer.toString();
    }
}