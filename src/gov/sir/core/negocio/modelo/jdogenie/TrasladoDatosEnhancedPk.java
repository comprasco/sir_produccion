package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TrasladoDatosPk;

/** Clase que define los atributos que identifican a Traslado */

public class TrasladoDatosEnhancedPk implements java.io.Serializable {

    public Long numeroTraslado;

    public TrasladoDatosEnhancedPk() {
    }

    public TrasladoDatosEnhancedPk(String s) {
        int p = 0;
        numeroTraslado = Long.parseLong(s.substring(p));
    }
    public TrasladoDatosPk getTrasladoDatosID(){
        TrasladoDatosPk id = new TrasladoDatosPk();
        id.numeroTraslado = numeroTraslado;
        return id;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrasladoDatosEnhancedPk)) return false;

        final TrasladoDatosEnhancedPk id = (TrasladoDatosEnhancedPk) o;

        if (this.numeroTraslado != null ? !numeroTraslado.equals(id.numeroTraslado) : id.numeroTraslado != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int)(numeroTraslado ^ (numeroTraslado >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(numeroTraslado);
        return buffer.toString();
    }
}