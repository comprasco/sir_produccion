package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a Complementacion     */
public class ComplementacionPk implements java.io.Serializable {

    public String idComplementacion;

    public ComplementacionPk() {
    }

    public ComplementacionPk(String s) {
        int i, p = 0;
        idComplementacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplementacionPk)) return false;

        final ComplementacionPk id = (ComplementacionPk) o;

        if (this.idComplementacion != null ? !idComplementacion.equals(id.idComplementacion) : id.idComplementacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idComplementacion != null ? idComplementacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idComplementacion);
        return buffer.toString();
    }
}