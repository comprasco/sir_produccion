package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoFundamentoPk;

/** Clase que define los atributos que identifican a Traslado */

public class TipoFundamentoEnhancedPk implements java.io.Serializable {

    public Long idTipoFundamento;

    public TipoFundamentoEnhancedPk() {
    }

    public TipoFundamentoEnhancedPk(String s) {
        int p = 0;
        idTipoFundamento = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoFundamentoEnhancedPk)) return false;

        final TipoFundamentoEnhancedPk id = (TipoFundamentoEnhancedPk) o;

        if (this.idTipoFundamento != null ? !idTipoFundamento.equals(id.idTipoFundamento) : id.idTipoFundamento != null) return false;
        return true;
    }
    public TipoFundamentoPk getTipoFundamentoID(){
        TipoFundamentoPk id = new TipoFundamentoPk();
        id.idTipoFundamento = idTipoFundamento;
        return id;
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