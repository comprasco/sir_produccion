package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Traslado */

public class TrasladoFundamentoPk implements java.io.Serializable {

    public Long tipoOrigen;
    public Long numeroTraslado;
    public Long idFundamento;

    public TrasladoFundamentoPk() {
    }

    public TrasladoFundamentoPk(String s) {
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrasladoFundamentoPk)) return false;

        final TrasladoFundamentoPk id = (TrasladoFundamentoPk) o;

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