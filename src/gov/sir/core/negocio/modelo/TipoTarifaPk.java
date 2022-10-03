package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoTarifa */

public class TipoTarifaPk implements java.io.Serializable {

    public String idTipo;

    public TipoTarifaPk() {
    }

    public TipoTarifaPk(String s) {
        int i, p = 0;
        idTipo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoTarifaPk)) return false;

        final TipoTarifaPk id = (TipoTarifaPk) o;

        if (this.idTipo != null ? !idTipo.equals(id.idTipo) : id.idTipo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipo != null ? idTipo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipo);
        return buffer.toString();
    }
}