package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.FundamentoPk;

/** Clase que define los atributos que identifican a Traslado */

public class FundamentoEnhancedPk implements java.io.Serializable {

    public Long idFundamento;

    public FundamentoEnhancedPk() {
    }

    public FundamentoEnhancedPk(String s) {
        int p = 0;
        idFundamento = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FundamentoEnhancedPk)) return false;

        final FundamentoEnhancedPk id = (FundamentoEnhancedPk) o;

        if (this.idFundamento != null ? !idFundamento.equals(id.idFundamento) : id.idFundamento != null) return false;
        return true;
    }
    public FundamentoPk getFundamentoID(){
        FundamentoPk id = new FundamentoPk();
        id.idFundamento = idFundamento;
        return id;
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