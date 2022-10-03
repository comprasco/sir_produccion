package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoPago */

public class TipoPagoPk implements java.io.Serializable {

    public long idTipoDocPago;

    public TipoPagoPk() {
    }

    public TipoPagoPk(String s) {
        int i, p = 0;
        idTipoDocPago = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoPagoPk)) return false;

        final TipoPagoPk id = (TipoPagoPk) o;

        if (this.idTipoDocPago != id.idTipoDocPago) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idTipoDocPago ^ (idTipoDocPago >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoDocPago);
        return buffer.toString();
    }
}