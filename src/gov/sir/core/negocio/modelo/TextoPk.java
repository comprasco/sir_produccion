package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class TextoPk implements java.io.Serializable {

    public String idCirculo;
    public String idLlave;

    public TextoPk() {
    }

    public TextoPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        idLlave = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextoPk)) return false;

        final TextoPk id = (TextoPk) o;

        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idLlave != null ? !idLlave.equals(id.idLlave) : id.idLlave != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (idLlave != null ? idLlave.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idLlave);
        return buffer.toString();
    }
}