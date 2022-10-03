package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TrasladoFundamentoPk;

/** Clase que define los atributos que identifican a Traslado */

public class TrasladoFundamentoEnhancedPk implements java.io.Serializable {

    public Long tipoOrigen;
    public Long numeroTraslado;
    public Long idFundamento;

    public TrasladoFundamentoEnhancedPk() {
    }

    public TrasladoFundamentoEnhancedPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        tipoOrigen = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i = s.indexOf('-', p);
        numeroTraslado = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i = s.indexOf(p);
        idFundamento = Long.parseLong(s.substring(p, i));
    }
    public TrasladoFundamentoPk getTrasladoFundamento(){
        TrasladoFundamentoPk id = new TrasladoFundamentoPk();
        id.idFundamento = idFundamento;
        id.numeroTraslado = numeroTraslado;
        id.tipoOrigen = tipoOrigen;   
        return id;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrasladoFundamentoEnhancedPk)) return false;

        final TrasladoFundamentoEnhancedPk id = (TrasladoFundamentoEnhancedPk) o;

        if (this.tipoOrigen != null ? !tipoOrigen.equals(id.tipoOrigen) : id.tipoOrigen != null) return false;
        if (this.numeroTraslado != null ? !numeroTraslado.equals(id.numeroTraslado) : id.numeroTraslado != null) return false;
        if (this.idFundamento != null ? !idFundamento.equals(id.idFundamento) : id.idFundamento != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int)(tipoOrigen ^ (tipoOrigen >>> 32));
        result = (29 * result) + (int)(numeroTraslado ^ (numeroTraslado >>> 32));
        result = (29 * result) + (int)(idFundamento ^ (idFundamento >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tipoOrigen);
        buffer.append("-");
        buffer.append(numeroTraslado);
        buffer.append("-");
        buffer.append(idFundamento);
        return buffer.toString();
    }
}