package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TrasladoPk;

/**
 * Application identity objectid-class.
 */
public class TrasladoEnhancedPk implements java.io.Serializable {

    public String idTraslado;

    public TrasladoEnhancedPk() {
    }

    public TrasladoEnhancedPk(String s) {
        int p = 0;
        idTraslado = s.substring(p);
    }
    

	public TrasladoEnhancedPk(TrasladoPk id) {
		idTraslado = id.idTraslado;
	}


	public TrasladoPk getTrasladoID() {
		TrasladoPk rta = new TrasladoPk();
		rta.idTraslado = idTraslado;
		return rta;
	}


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrasladoEnhancedPk)) return false;

        final TrasladoEnhancedPk id = (TrasladoEnhancedPk) o;

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