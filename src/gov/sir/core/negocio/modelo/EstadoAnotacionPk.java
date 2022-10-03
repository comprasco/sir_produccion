package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a EstadoAnotacion  */
public class EstadoAnotacionPk implements java.io.Serializable {

    public String idEstadoAn;

    public EstadoAnotacionPk() {
    }

    public EstadoAnotacionPk(String s) {
        int i, p = 0;
        idEstadoAn = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstadoAnotacionPk)) return false;

        final EstadoAnotacionPk id = (EstadoAnotacionPk) o;

        if (this.idEstadoAn != null ? !idEstadoAn.equals(id.idEstadoAn) : id.idEstadoAn != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idEstadoAn != null ? idEstadoAn.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEstadoAn);
        return buffer.toString();
    }
}