package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoFotocopia */

public class TipoFotocopiaPk implements java.io.Serializable {

    public String idTipoFotocopia;

    public TipoFotocopiaPk() {
    }

    public TipoFotocopiaPk(String s) {
        int i, p = 0;
        idTipoFotocopia = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoFotocopiaPk)) return false;

        final TipoFotocopiaPk id = (TipoFotocopiaPk) o;

        if (this.idTipoFotocopia != null ? !idTipoFotocopia.equals(id.idTipoFotocopia) : id.idTipoFotocopia != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoFotocopia != null ? idTipoFotocopia.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoFotocopia);
        return buffer.toString();
    }
}