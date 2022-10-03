package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Traslado */

public class FundamentoPk implements java.io.Serializable {

    public Long idFundamento;

    public FundamentoPk() {
    }

    public FundamentoPk(String s) {
        int p = 0;
        idFundamento = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FundamentoPk)) return false;

        final FundamentoPk id = (FundamentoPk) o;

        if (this.idFundamento != null ? !idFundamento.equals(id.idFundamento) : id.idFundamento != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int)(idFundamento ^ (idFundamento >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFundamento);
        return buffer.toString();
    }
}