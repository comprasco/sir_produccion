package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoCertificado */

public class TipoCertificadoPk implements java.io.Serializable {

    public String idTipoCertificado;

    public TipoCertificadoPk() {
    }

    public TipoCertificadoPk(String s) {
        int i, p = 0;
        idTipoCertificado = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCertificadoPk)) return false;

        final TipoCertificadoPk id = (TipoCertificadoPk) o;

        if (this.idTipoCertificado != null ? !idTipoCertificado.equals(id.idTipoCertificado) : id.idTipoCertificado != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoCertificado != null ? idTipoCertificado.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoCertificado);
        return buffer.toString();
    }
}