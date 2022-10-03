package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Traslado */

public class TrasladoDatosPk implements java.io.Serializable {

    public Long numeroTraslado;

    public TrasladoDatosPk() {
    }

    public TrasladoDatosPk(String s) {
        int p = 0;
        numeroTraslado = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrasladoDatosPk)) return false;

        final TrasladoDatosPk id = (TrasladoDatosPk) o;

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