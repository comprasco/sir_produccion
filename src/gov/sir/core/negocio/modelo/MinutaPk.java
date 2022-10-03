package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Minuta  */
public class MinutaPk implements java.io.Serializable {

    public String idMinuta;

    public MinutaPk() {
    }

    public MinutaPk(String s) {
        int i, p = 0;
        idMinuta = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinutaPk)) return false;

        final MinutaPk id = (MinutaPk) o;

        if (this.idMinuta != null ? !idMinuta.equals(id.idMinuta) : id.idMinuta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idMinuta != null ? idMinuta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idMinuta);
        return buffer.toString();
    }
}