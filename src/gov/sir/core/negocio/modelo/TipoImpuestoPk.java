package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoImpuesto */

public class TipoImpuestoPk implements java.io.Serializable {

    public String idTipoImpuesto;

    public TipoImpuestoPk() {
    }

    public TipoImpuestoPk(String s) {
        int i, p = 0;
        idTipoImpuesto = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoImpuestoPk)) return false;

        final TipoImpuestoPk id = (TipoImpuestoPk) o;

        if (this.idTipoImpuesto != null ? !idTipoImpuesto.equals(id.idTipoImpuesto) : id.idTipoImpuesto != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoImpuesto != null ? idTipoImpuesto.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoImpuesto);
        return buffer.toString();
    }
}