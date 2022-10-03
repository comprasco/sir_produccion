package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Traslado */

public class TrasladoPk implements java.io.Serializable {

    public String idTraslado;

    public TrasladoPk() {
    }

    public TrasladoPk(String s) {
        int p = 0;
        idTraslado = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrasladoPk)) return false;

        final TrasladoPk id = (TrasladoPk) o;

        if (this.idTraslado != null ? !idTraslado.equals(id.idTraslado) : id.idTraslado != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTraslado != null ? idTraslado.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTraslado);
        return buffer.toString();
    }
}