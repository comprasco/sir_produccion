package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a EntidadPublica  */
public class EntidadPublicaPk implements java.io.Serializable {

    public String idEntidadPublica;

    public EntidadPublicaPk() {
    }

    public EntidadPublicaPk(String s) {
        int i, p = 0;
        idEntidadPublica = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntidadPublicaPk)) return false;

        final EntidadPublicaPk id = (EntidadPublicaPk) o;

        if (this.idEntidadPublica != null ? !idEntidadPublica.equals(id.idEntidadPublica) : id.idEntidadPublica != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idEntidadPublica != null ? idEntidadPublica.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEntidadPublica);
        return buffer.toString();
    }
}