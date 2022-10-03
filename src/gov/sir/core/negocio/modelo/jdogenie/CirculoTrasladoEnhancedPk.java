/**
 * 
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CirculoTrasladoPk;
import gov.sir.core.negocio.modelo.TrasladoPk;

public class CirculoTrasladoEnhancedPk implements java.io.Serializable {

    public String idTraslado;

    public CirculoTrasladoEnhancedPk() {
    }

    public CirculoTrasladoEnhancedPk(String s) {
        int p = 0;
        idTraslado = s.substring(p);
    }
    

	public CirculoTrasladoEnhancedPk(TrasladoPk id) {
		idTraslado = id.idTraslado;
	}


	public CirculoTrasladoPk getTrasladoID() {
		CirculoTrasladoPk rta = new CirculoTrasladoPk();
		rta.idTraslado = idTraslado;
		return rta;
	}


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CirculoTrasladoEnhancedPk)) return false;

        final CirculoTrasladoEnhancedPk id = (CirculoTrasladoEnhancedPk) o;

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