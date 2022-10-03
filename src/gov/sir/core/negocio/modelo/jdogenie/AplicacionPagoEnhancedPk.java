/**
 * 
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.AplicacionPagoPk;

public class AplicacionPagoEnhancedPk implements java.io.Serializable {
    public String idDocumentoPago;
    public String idLiquidacion;
    public String idSolicitud;

    public AplicacionPagoEnhancedPk() {
    }

	public AplicacionPagoEnhancedPk(AplicacionPagoPk id){
		idDocumentoPago = id.idDocumentoPago;
		idLiquidacion = id.idLiquidacion;
		idSolicitud = id.idSolicitud;
	}
	
	public AplicacionPagoPk getAplicacionPagoID(){
		AplicacionPagoPk id = new AplicacionPagoPk();
		id.idDocumentoPago= idDocumentoPago;
		id.idLiquidacion = idLiquidacion;
		id.idSolicitud = idSolicitud;
		return id;
	}

    public AplicacionPagoEnhancedPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idDocumentoPago = s.substring(p, i);
		p = i + 1;
        i = s.indexOf('-', p);
        idLiquidacion = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);

    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AplicacionPagoEnhancedPk)) {
            return false;
        }

        final AplicacionPagoEnhancedPk id = (AplicacionPagoEnhancedPk) o;

        if ((this.idDocumentoPago != null)
                ? (!idDocumentoPago.equals(id.idDocumentoPago))
                    : (id.idDocumentoPago != null)) {
            return false;
        }

        if ((this.idLiquidacion != null)
                ? (!idLiquidacion.equals(id.idLiquidacion))
                    : (id.idLiquidacion != null)) {
            return false;
        }

        if ((this.idSolicitud != null)
                ? (!idSolicitud.equals(id.idSolicitud))
                    : (id.idSolicitud != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idDocumentoPago != null) ? idDocumentoPago.hashCode() : 0);
        result = (29 * result) +
            ((idLiquidacion != null) ? idLiquidacion.hashCode() : 0);
        result = (29 * result) +
            ((idSolicitud != null) ? idSolicitud.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDocumentoPago);
        buffer.append('-');
        buffer.append(idLiquidacion);
        buffer.append('-');
        buffer.append(idSolicitud);

        return buffer.toString();
    }
}