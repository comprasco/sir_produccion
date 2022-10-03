package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoHorarioRepartoNotarial */
public class TipoHorarioNotarialPk implements java.io.Serializable {

    public String idTipoHorarioNotarial;

    public TipoHorarioNotarialPk() {
    }

    public TipoHorarioNotarialPk(String s) {
        int i, p = 0;
        idTipoHorarioNotarial = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCertificadoPk)) return false;

        final TipoHorarioNotarialPk id = (TipoHorarioNotarialPk) o;

        if (this.idTipoHorarioNotarial != null ? !idTipoHorarioNotarial.equals(id.idTipoHorarioNotarial) : id.idTipoHorarioNotarial != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoHorarioNotarial != null ? idTipoHorarioNotarial.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoHorarioNotarial);
        return buffer.toString();
    }
}