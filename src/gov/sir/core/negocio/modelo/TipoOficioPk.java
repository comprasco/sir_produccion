package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoOficio */

public class TipoOficioPk implements java.io.Serializable {

    public String idTipoOficio;

    public TipoOficioPk() {
    }

    public TipoOficioPk(String s) {
        int i, p = 0;
        idTipoOficio = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoOficioPk)) return false;

        final TipoOficioPk id = (TipoOficioPk) o;

        if (this.idTipoOficio != null ? !idTipoOficio.equals(id.idTipoOficio) : id.idTipoOficio != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoOficio != null ? idTipoOficio.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoOficio);
        return buffer.toString();
    }
}