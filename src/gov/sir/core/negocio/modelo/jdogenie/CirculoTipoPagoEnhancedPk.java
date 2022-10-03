package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CirculoTipoPagoPk;

/**
 * Application identity objectid-class.
 */
public class CirculoTipoPagoEnhancedPk implements java.io.Serializable {

    public String idCirculoTipoPago;

    public CirculoTipoPagoEnhancedPk() {
    }

    public CirculoTipoPagoEnhancedPk(CirculoTipoPagoPk oid) {
        idCirculoTipoPago = oid.idCirculoTipoPago;
                
    }

    public CirculoTipoPagoPk getCirculoTipoPagoID() {
        CirculoTipoPagoPk rta = new CirculoTipoPagoPk();
        rta.idCirculoTipoPago = idCirculoTipoPago;
        return rta;
    }

    public CirculoTipoPagoEnhancedPk(String s) {
        int p = 0;
        idCirculoTipoPago = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CirculoTipoPagoEnhancedPk)) {
            return false;
        }

        final CirculoTipoPagoEnhancedPk id = (CirculoTipoPagoEnhancedPk) o;

        if (this.idCirculoTipoPago != null ? !idCirculoTipoPago.equals(id.idCirculoTipoPago) : id.idCirculoTipoPago != null) {
            return false;
        }
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
