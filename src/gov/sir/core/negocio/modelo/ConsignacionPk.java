package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class ConsignacionPk implements java.io.Serializable {

	public String idSolicitud;
    public String idDocPago;
    

    public ConsignacionPk() {
    }

    public ConsignacionPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        idDocPago = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsignacionPk)) return false;

        final ConsignacionPk id = (ConsignacionPk) o;

        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idDocPago != null ? !idDocPago.equals(id.idDocPago) : id.idDocPago != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idDocPago != null ? idDocPago.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idDocPago);
        return buffer.toString();
    }
}
