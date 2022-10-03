package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Pago	  */

public class PagoPk implements java.io.Serializable {
    /** Identificador de la liquidación */
    public String idLiquidacion;

    /** Identificador de la solicitud */
    public String idSolicitud;


    public PagoPk() {
    }

    /**
     * Constructor que recibe el identificador del pago
     * @param s cadena con la siguiente estructura: idLiquidacion-idSolicitud
     */
    public PagoPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idLiquidacion = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PagoPk)) {
            return false;
        }

        final PagoPk id = (PagoPk) o;

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
            ((idLiquidacion != null) ? idLiquidacion.hashCode() : 0);
        result = (29 * result) +
            ((idSolicitud != null) ? idSolicitud.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idLiquidacion);
        buffer.append('-');
        buffer.append(idSolicitud);

        return buffer.toString();
    }
}