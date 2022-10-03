package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a Auditoria
 */
public class EstacionReciboAuditoriaPk implements java.io.Serializable {

    public int idAuditoria;

    public EstacionReciboAuditoriaPk() {
    }

    public EstacionReciboAuditoriaPk(String s) {
        int i, p = 0;
        idAuditoria = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditoriaPk)) return false;

        final AuditoriaPk id = (AuditoriaPk) o;

        if (this.idAuditoria != id.idAuditoria) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) idAuditoria;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAuditoria);
        return buffer.toString();
    }
}