package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class CirculoRelacionPk implements java.io.Serializable {

    public String anio;
    public String idCirculo;

    public CirculoRelacionPk() {
    }

    public CirculoRelacionPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        anio = s.substring(p, i);
        p = i + 1;
        idCirculo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CirculoRelacionPk)) return false;

        final CirculoRelacionPk id = (CirculoRelacionPk) o;

        if (this.anio != null ? !anio.equals(id.anio) : id.anio != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anio != null ? anio.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(anio);
        buffer.append('-');
        buffer.append(idCirculo);
        return buffer.toString();
    }
}