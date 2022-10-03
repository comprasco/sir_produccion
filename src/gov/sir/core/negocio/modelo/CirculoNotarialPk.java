package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a CirculoNotarial
 */
public class CirculoNotarialPk implements java.io.Serializable {

    public String idCirculo;

    public CirculoNotarialPk() {
    }

    public CirculoNotarialPk(String s) {
        int i, p = 0;
        idCirculo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CirculoNotarialPk)) return false;

        final CirculoNotarialPk id = (CirculoNotarialPk) o;

        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);
        return buffer.toString();
    }
}