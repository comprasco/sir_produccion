package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a OPLookupTypes	  */

public class OPLookupTypesPk implements java.io.Serializable {

    public String tipo;

    public OPLookupTypesPk() {
    }

    public OPLookupTypesPk(String s) {
        int i, p = 0;
        tipo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OPLookupTypesPk)) return false;

        final OPLookupTypesPk id = (OPLookupTypesPk) o;

        if (this.tipo != null ? !tipo.equals(id.tipo) : id.tipo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tipo);
        return buffer.toString();
    }
}