package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a Complementacion     */
public class ComplementacionConflictivaComplementacionPk implements java.io.Serializable {

    public String idAuditoria;

    public ComplementacionConflictivaComplementacionPk() {
    }

    public ComplementacionConflictivaComplementacionPk(String s) {
        int i, p = 0;
        idAuditoria = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplementacionConflictivaComplementacionPk)) return false;

        final ComplementacionConflictivaComplementacionPk id = (ComplementacionConflictivaComplementacionPk) o;

        if (this.idAuditoria != null ? !idAuditoria.equals(id.idAuditoria) : id.idAuditoria != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAuditoria != null ? idAuditoria.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAuditoria);
        return buffer.toString();
    }
}