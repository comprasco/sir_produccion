package gov.sir.core.negocio.modelo;


/**
 * Application identity objectid-class.
 */
public class SolicitantePk implements java.io.Serializable {

	public String idSolicitud;
    public String idCiudadano;
    

    public SolicitantePk() {
    }

    public SolicitantePk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        idCiudadano = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitantePk)) return false;

        final SolicitantePk id = (SolicitantePk) o;

        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idCiudadano != null ? !idCiudadano.equals(id.idCiudadano) : id.idCiudadano != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idCiudadano != null ? idCiudadano.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idCiudadano);
        return buffer.toString();
    }
}
