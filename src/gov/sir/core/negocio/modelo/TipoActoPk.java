package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoActo */
public class TipoActoPk implements java.io.Serializable {

    public String idTipoActo;

    public TipoActoPk() {
    }

    public TipoActoPk(String s) {
        int i, p = 0;
        idTipoActo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoActoPk)) return false;

        final TipoActoPk id = (TipoActoPk) o;

        if (this.idTipoActo != null ? !idTipoActo.equals(id.idTipoActo) : id.idTipoActo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoActo != null ? idTipoActo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoActo);
        return buffer.toString();
    }
}