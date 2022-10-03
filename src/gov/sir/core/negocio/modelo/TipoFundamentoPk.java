package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Traslado */

public class TipoFundamentoPk implements java.io.Serializable {

    public Long idTipoFundamento;

    public TipoFundamentoPk() {
    }

    public TipoFundamentoPk(String s) {
        int p = 0;
        idTipoFundamento = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoFundamentoPk)) return false;

        final TipoFundamentoPk id = (TipoFundamentoPk) o;

        if (this.idTipoFundamento != null ? !idTipoFundamento.equals(id.idTipoFundamento) : id.idTipoFundamento != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int)(idTipoFundamento ^ (idTipoFundamento >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoFundamento);
        return buffer.toString();
    }
}