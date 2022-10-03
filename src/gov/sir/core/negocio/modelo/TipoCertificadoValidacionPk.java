package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoCertificadoValidacion */

public class TipoCertificadoValidacionPk implements java.io.Serializable {

    public String idTipoCertificado;
    public String idValidacion;

    public TipoCertificadoValidacionPk() {
    }

    public TipoCertificadoValidacionPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idTipoCertificado = s.substring(p, i);
        p = i + 1;
        idValidacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCertificadoValidacionPk)) return false;

        final TipoCertificadoValidacionPk id = (TipoCertificadoValidacionPk) o;

        if (this.idTipoCertificado != null ? !idTipoCertificado.equals(id.idTipoCertificado) : id.idTipoCertificado != null) return false;
        if (this.idValidacion != null ? !idValidacion.equals(id.idValidacion) : id.idValidacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoCertificado != null ? idTipoCertificado.hashCode() : 0);
        result = 29 * result + (idValidacion != null ? idValidacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoCertificado);
        buffer.append('-');
        buffer.append(idValidacion);
        return buffer.toString();
    }
}