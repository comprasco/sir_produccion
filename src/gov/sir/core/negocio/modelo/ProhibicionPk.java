package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Prohibición	  */

public class ProhibicionPk implements java.io.Serializable {

    public String idProhibicion;

    public ProhibicionPk() {
    }

    public ProhibicionPk(String s) {
        int i, p = 0;
        idProhibicion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProhibicionPk)) return false;

        final ProhibicionPk id = (ProhibicionPk) o;

        if (this.idProhibicion != null ? !idProhibicion.equals(id.idProhibicion) : id.idProhibicion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idProhibicion != null ? idProhibicion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idProhibicion);
        return buffer.toString();
    }
}