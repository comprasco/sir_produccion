package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Validacion */

public class ValidacionPk implements java.io.Serializable {

    public String idValidacion;

    public ValidacionPk() {
    }

    public ValidacionPk(String s) {
        int i, p = 0;
        idValidacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidacionPk)) return false;

        final ValidacionPk id = (ValidacionPk) o;

        if (this.idValidacion != null ? !idValidacion.equals(id.idValidacion) : id.idValidacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idValidacion != null ? idValidacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idValidacion);
        return buffer.toString();
    }
}