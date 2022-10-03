package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoCalculo */

public class TipoCalculoPk implements java.io.Serializable {

    public String idTipoCalculo;

    public TipoCalculoPk() {
    }

    public TipoCalculoPk(String s) {
        int i, p = 0;
        idTipoCalculo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCalculoPk)) return false;

        final TipoCalculoPk id = (TipoCalculoPk) o;

        if (this.idTipoCalculo != null ? !idTipoCalculo.equals(id.idTipoCalculo) : id.idTipoCalculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoCalculo != null ? idTipoCalculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoCalculo);
        return buffer.toString();
    }
}