package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoPagoPk;

/**
 * Application identity objectid-class.
 */
public class TipoPagoEnhancedPk implements java.io.Serializable {

    public long idTipoDocPago;

    public TipoPagoEnhancedPk() {
    }

    public TipoPagoEnhancedPk(String s) {
        int i, p = 0;
        idTipoDocPago = Long.parseLong(s.substring(p));
    }
    

	public TipoPagoEnhancedPk(TipoPagoPk id) {
		idTipoDocPago = id.idTipoDocPago;
	}


	public TipoPagoPk getUsuarioID() {
		TipoPagoPk rta = new TipoPagoPk();
		rta.idTipoDocPago = idTipoDocPago;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoPagoEnhancedPk)) return false;

        final TipoPagoEnhancedPk id = (TipoPagoEnhancedPk) o;

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