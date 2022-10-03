package gov.sir.core.negocio.modelo;

/**
 * Clase Interna que define la Llave primaria del objeto
 */
public class AplicacionPagoPk implements java.io.Serializable {
    /** Identificador del documento de pago */
    public String idDocumentoPago;
    
    /** Identificador de la liquidación */
    public String idLiquidacion;
    
    /** Identificador de la solicitud */
    public String idSolicitud;

    /**  Cosntructor por defecto */
    public AplicacionPagoPk() {
    }

    /** 
     * Constructor que llena todos los atributos de la llave Primaria
     * @param s Debe ser idDocumento-idLiquidacion-idSolicitud
     */
    public AplicacionPagoPk(String s) {
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

        if (!(o instanceof AplicacionPagoPk)) {
            return false;
        }

        final AplicacionPagoPk id = (AplicacionPagoPk) o;

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