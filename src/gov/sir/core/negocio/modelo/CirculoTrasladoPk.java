/**
 * 
 */
package gov.sir.core.negocio.modelo;

public class CirculoTrasladoPk implements java.io.Serializable {

    public String idTraslado;

    public CirculoTrasladoPk() {
    }

    public CirculoTrasladoPk(String s) {
        int p = 0;
        idTraslado = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CirculoTrasladoPk)) return false;

        final CirculoTrasladoPk id = (CirculoTrasladoPk) o;

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