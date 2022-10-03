package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Oficio  */

public class OficioPk implements java.io.Serializable {

    public String idOficio;

    public OficioPk() {
    }

    public OficioPk(String s) {
        int i, p = 0;
        idOficio = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OficioPk)) return false;

        final OficioPk id = (OficioPk) o;

        if (this.idOficio != null ? !idOficio.equals(id.idOficio) : id.idOficio != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idOficio != null ? idOficio.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idOficio);
        return buffer.toString();
    }
}