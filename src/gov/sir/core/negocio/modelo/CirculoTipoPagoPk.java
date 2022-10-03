package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a CirculoTipoPago  */
public class CirculoTipoPagoPk implements java.io.Serializable {

    public String idCirculoTipoPago;

    public CirculoTipoPagoPk() {
    }

    public CirculoTipoPagoPk(String s) {
        int p = 0;
	idCirculoTipoPago = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CirculoTipoPagoPk)) return false;

        final CirculoTipoPagoPk id = (CirculoTipoPagoPk) o;

        if (this.idCirculoTipoPago != null ? !idCirculoTipoPago.equals(id.idCirculoTipoPago) : id.idCirculoTipoPago != null) return false;
        
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculoTipoPago != null ? idCirculoTipoPago.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculoTipoPago);
        return buffer.toString();
    }
}